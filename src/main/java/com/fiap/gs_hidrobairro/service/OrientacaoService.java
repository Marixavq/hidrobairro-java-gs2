
package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.Leitura;
import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import com.fiap.gs_hidrobairro.model.StatusAlerta;
import com.fiap.gs_hidrobairro.repository.AlertaRepository;
import com.fiap.gs_hidrobairro.repository.LeituraRepository;
import com.fiap.gs_hidrobairro.repository.PontoMonitoramentoRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrientacaoService {

    private final ChatClient chatClient;
    private final PontoMonitoramentoRepository pontoRepository;
    private final LeituraRepository leituraRepository;
    private final AlertaRepository alertaRepository;

    public OrientacaoService(
            ChatClient chatClient,
            PontoMonitoramentoRepository pontoRepository,
            LeituraRepository leituraRepository,
            AlertaRepository alertaRepository) {

        this.chatClient = chatClient;
        this.pontoRepository = pontoRepository;
        this.leituraRepository = leituraRepository;
        this.alertaRepository = alertaRepository;
    }

    public String gerarOrientacao(String pergunta) {

        String perguntaNormalizada = normalizar(pergunta);

        List<PontoMonitoramento> pontos = pontoRepository.findAll();

        List<PontoMonitoramento> pontosEncontrados = pontos.stream()
                .filter(p -> perguntaNormalizada.contains(normalizar(p.getNome())))
                .toList();

        if (pontosEncontrados.size() > 1) {
            int maiorNome = pontosEncontrados.stream()
                    .mapToInt(p -> p.getNome().length())
                    .max()
                    .orElse(0);

            pontosEncontrados = pontosEncontrados.stream()
                    .filter(p -> p.getNome().length() == maiorNome)
                    .toList();
        }

        if (pontosEncontrados.isEmpty()) {
            return "Não consegui identificar o ponto de monitoramento mencionado. Informe o nome de um ponto cadastrado no HidroBairro Sul.";
        }

        if (pontosEncontrados.size() > 1) {
            String nomes = pontosEncontrados.stream()
                    .map(PontoMonitoramento::getNome)
                    .toList()
                    .toString();

            return "Encontrei mais de um ponto correspondente: "
                    + nomes + ". Especifique qual local deseja consultar.";
        }

        PontoMonitoramento ponto = pontosEncontrados.get(0);

        if (ponto.getAtivo() != 'S') {
            return "O ponto " + ponto.getNome()
                    + " está desativado e não possui monitoramento ativo.";
        }

        Leitura leitura = leituraRepository
                .findTopByPonto_IdOrderByColetadoEmDesc(ponto.getId())
                .orElse(null);

        Alerta alerta = alertaRepository
                .findByPontoIdAndStatus(ponto.getId(), StatusAlerta.ATIVO)
                .orElse(null);

        String contexto = """
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
                leitura != null
                        ? "Nível: " + leitura.getNivelCm() + " cm"
                        + "\nHorário: " + leitura.getColetadoEm()
                        + "\nOrigem: " + leitura.getOrigem()
                        : "Nenhuma leitura registrada.",
                alerta != null
                        ? "Severidade: " + alerta.getSeveridade()
                        + "\nMensagem: " + alerta.getMensagem()
                        + "\nCriado em: " + alerta.getCriadoEm()
                        : "Nenhum alerta ativo registrado."
        );

        return chatClient.prompt()
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
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}
