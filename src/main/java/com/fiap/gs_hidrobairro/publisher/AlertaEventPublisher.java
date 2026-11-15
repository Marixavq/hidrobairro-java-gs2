package com.fiap.gs_hidrobairro.publisher;

import com.fiap.gs_hidrobairro.event.AlertaCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlertaEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(AlertaEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public AlertaEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.exchange.alertas}")
    private String alertaExchange;

    @Value("${rabbitmq.routing-key.alerta-criado}")
    private String alertaRoutingKey;

    public void publicarAlertaCriado(AlertaCriadoEvent evento) {

        log.info("Publicando evento de alerta criado: {}", evento.alertaId());

        rabbitTemplate.convertAndSend(
                alertaExchange,
                alertaRoutingKey,
                evento
        );

        log.info("Evento enviado ao RabbitMQ. Routing key: {}", alertaRoutingKey);
    }
}