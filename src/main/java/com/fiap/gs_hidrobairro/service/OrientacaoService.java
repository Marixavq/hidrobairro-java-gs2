
package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.Leitura;
import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import com.fiap.gs_hidrobairro.model.enums.StatusAlerta;
import com.fiap.gs_hidrobairro.repository.AlertaRepository;
import com.fiap.gs_hidrobairro.repository.LeituraRepository;
import com.fiap.gs_hidrobairro.repository.PontoMonitoramentoRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrientacaoService {

    private static final Logger log = LoggerFactory.getLogger(OrientacaoService.class);

    private final ChatClient chatClient;
    private final PontoMonitoramentoRepository pontoRepository;
    private final LeituraRepository leituraRepository;
    private final AlertaRepository alertaRepository;
    private final Timer tempoOrientacaoGemini;

    public OrientacaoService(
            ChatClient chatClient,
            PontoMonitoramentoRepository pontoRepository,
            LeituraRepository leituraRepository,
            AlertaRepository alertaRepository,
            MeterRegistry meterRegistry) {

        this.chatClient = chatClient;
        this.pontoRepository = pontoRepository;
        this.leituraRepository = leituraRepository;
        this.alertaRepository = alertaRepository;

        this.tempoOrientacaoGemini = Timer.builder("hidrobairro.ia.tempo.resposta")
                .description("Tempo de resposta do Gemini nas orientações preventivas")
                .register(meterRegistry);
    }

    public String gerarOrientacao(String pergunta) {

        log.info("Iniciando consulta...");

        // 1. Identificar o ponto mencionado pelo morador
        List<PontoMonitoramento> pontos = encontrarPontos(pergunta);

        if (pontos.isEmpty()) {
            return "Não consegui identificar o ponto de monitoramento mencionado. Informe o nome de um ponto cadastrado no HidroBairro Sul.";
        }

        if (pontos.size() > 1) {
            return solicitarEspecificacao(pontos);
        }

        PontoMonitoramento ponto = pontos.get(0);

        log.info("Ponto identificado: {}", ponto.getNome());

        // 2. Verificar se o ponto está ativo
        if (ponto.getAtivo() != 'S') {
            return "O ponto " + ponto.getNome()
                    + " está desativado e não possui monitoramento ativo.";
        }

        // 3. Consultar os dados do ponto no Oracle
        Leitura leitura = buscarUltimaLeitura(ponto);
        Alerta alerta = buscarAlertaAtivo(ponto);

        log.info("Dados do Oracle consultados.");

        // 4. Preparar as informações para a IA
        String contexto = montarContexto(ponto, leitura, alerta);

        // 5. Solicitar orientação ao Gemini
        return consultarGemini(pergunta, contexto);
    }

    private List<PontoMonitoramento> encontrarPontos(String pergunta) {
        String perguntaNormalizada = normalizar(pergunta);

        List<PontoMonitoramento> pontosEncontrados = pontoRepository.findAll()
                .stream()
                .filter(ponto ->
                        perguntaNormalizada.contains(normalizar(ponto.getNome()))
                                || perguntaNormalizada.contains(normalizar(ponto.getBairro())))
                .toList();

        // Se houver vários resultados, priorizar o nome mais específico
        if (pontosEncontrados.size() > 1) {

            int maiorNome = pontosEncontrados.stream()
                    .mapToInt(ponto -> ponto.getNome().length())
                    .max()
                    .orElse(0);

            pontosEncontrados = pontosEncontrados.stream()
                    .filter(ponto -> ponto.getNome().length() == maiorNome)
                    .toList();
        }

        return pontosEncontrados;
    }

    private String solicitarEspecificacao(List<PontoMonitoramento> pontos) {
        String nomes = pontos.stream()
                .map(PontoMonitoramento::getNome)
                .toList()
                .toString();

        return "Encontrei mais de um ponto correspondente: "
                + nomes + ". Especifique qual local deseja consultar.";
    }

    private Leitura buscarUltimaLeitura(PontoMonitoramento ponto) {
        return leituraRepository
                .findTopByPontoIdOrderByColetadoEmDesc(ponto.getId())
                .orElse(null);
    }

    private Alerta buscarAlertaAtivo(PontoMonitoramento ponto) {
        return alertaRepository
                .findByPontoIdAndStatus(ponto.getId(), StatusAlerta.ATIVO)
                .orElse(null);
    }

    private String montarContexto(
            PontoMonitoramento ponto,
            Leitura leitura,
            Alerta alerta) {

        return """
                Data e hora da consulta: %s
                
                Ponto: %s
                Bairro: %s
                Subprefeitura: %s
                Limiar de alerta: %s cm
                
                Última leitura:
                %s
                
                Alerta ativo:
                %s
                """.formatted(
                LocalDateTime.now(),
                ponto.getNome(),
                ponto.getBairro(),
                ponto.getSubprefeitura(),
                ponto.getLimiarAlertaCm(),
                formatarLeitura(leitura),
                formatarAlerta(alerta)
        );
    }

    private String formatarLeitura(Leitura leitura) {
        if (leitura == null) {
            return "Nenhuma leitura registrada.";
        }

        return "Nível: " + leitura.getNivelCm() + " cm"
                + "\nHorário: " + leitura.getColetadoEm()
                + "\nOrigem: " + leitura.getOrigem();
    }

    private String formatarAlerta(Alerta alerta) {
        if (alerta == null) {
            return "Nenhum alerta ativo registrado.";
        }

        return "Severidade: " + alerta.getSeveridade()
                + "\nMensagem: " + alerta.getMensagem()
                + "\nCriado em: " + alerta.getCriadoEm();
    }

    private String consultarGemini(String pergunta, String contexto) {

        long inicio = System.nanoTime();

        String resposta = chatClient.prompt()
                .user("""
                        Pergunta do morador:
                        %s
                        
                        Dados consultados no sistema HidroBairro Sul:
                        %s
                        
                        Elabore uma orientação preventiva com base
                        exclusivamente nessas informações.
                        """.formatted(pergunta, contexto))
                .call()
                .content();

        long duracao = System.nanoTime() - inicio;

        tempoOrientacaoGemini.record(duracao, TimeUnit.NANOSECONDS);

        log.info("Gemini respondeu!");

        return resposta;
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}
