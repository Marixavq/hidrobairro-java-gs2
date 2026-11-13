package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.model.*;
import com.fiap.gs_hidrobairro.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    @Autowired
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @Transactional
    public void avaliarLeitura(Leitura leitura) {

        PontoMonitoramento ponto = leitura.getPonto();

        BigDecimal nivel = leitura.getNivelCm();
        BigDecimal limiar = ponto.getLimiarAlertaCm();

        Optional<Alerta> alertaExistente =
                alertaRepository.findByPontoIdAndStatus(ponto.getId(), StatusAlerta.ATIVO);

        // Se o nível estiver abaixo do limiar
        if (nivel.compareTo(limiar) < 0) {

            // Encerra o alerta ativo, caso exista
            if (alertaExistente.isPresent()) {
                Alerta alerta = alertaExistente.get();

                alerta.setStatus(StatusAlerta.ENCERRADO);
                alerta.setEncerradoEm(LocalDateTime.now());

                alertaRepository.save(alerta);
            }
            return;
        }

        // Define a severidade do alerta
        BigDecimal limiteCritico = limiar.multiply(new BigDecimal("1.5"));

        SeveridadeAlerta severidade = nivel.compareTo(limiteCritico) >= 0
                        ? SeveridadeAlerta.CRITICO
                        : SeveridadeAlerta.ATENCAO;

        // Se já existe alerta, atualiza
        if (alertaExistente.isPresent()) {

            Alerta alerta = alertaExistente.get();

            alerta.setLeitura(leitura);
            alerta.setNivelCm(nivel);
            alerta.setSeveridade(severidade);
            alerta.setMensagem("Nível de água em " + nivel + " cm no ponto " + ponto.getNome());

            alertaRepository.save(alerta);

        } else {

            // Se não existe, cria um novo
            Alerta alerta = new Alerta();

            alerta.setPonto(ponto);
            alerta.setLeitura(leitura);
            alerta.setNivelCm(nivel);
            alerta.setSeveridade(severidade);
            alerta.setMensagem("Nível de água em " + nivel + " cm no ponto " + ponto.getNome());
            alerta.setStatus(StatusAlerta.ATIVO);
            alerta.setCriadoEm(LocalDateTime.now());

            alertaRepository.save(alerta);
        }
    }
}