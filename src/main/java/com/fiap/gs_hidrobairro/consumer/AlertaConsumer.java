package com.fiap.gs_hidrobairro.consumer;

import com.fiap.gs_hidrobairro.event.AlertaCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlertaConsumer {

    private static final Logger log = LoggerFactory.getLogger(AlertaConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.alertas}")
    public void consumirAlertaCriado(AlertaCriadoEvent evento) {

        log.info("Evento de alerta recebido do RabbitMQ: {}", evento.alertaId());

        log.info("Alerta processado: {} | Ponto: {} | Severidade: {} | Nível: {} cm",
                evento.alertaId(),
                evento.nomePonto(),
                evento.severidade(),
                evento.nivelCm());
    }
}