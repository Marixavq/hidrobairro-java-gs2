package com.fiap.gs_hidrobairro.publisher;

import com.fiap.gs_hidrobairro.event.AlertaCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

@Service
public class AlertaEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(AlertaEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final JsonMapper jsonMapper;

    public AlertaEventPublisher(RabbitTemplate rabbitTemplate, JsonMapper jsonMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Value("${rabbitmq.exchange.alertas}")
    private String alertaExchange;

    @Value("${rabbitmq.routing-key.alerta-criado}")
    private String alertaRoutingKey;


    public void publicarAlertaCriado(AlertaCriadoEvent evento) {
        try {
            String mensagem = jsonMapper.writeValueAsString(evento);

            log.info("Publicando evento de alerta criado: {}", evento.alertaId());

            rabbitTemplate.convertAndSend(
                    alertaExchange,
                    alertaRoutingKey,
                    mensagem
            );

            log.info("Evento enviado ao RabbitMQ. Routing key: {}", alertaRoutingKey);

        } catch (JacksonException e) {
            log.error("Erro ao serializar evento de alerta: {}", e.getMessage(), e);

            throw new IllegalStateException("Erro ao serializar evento de alerta", e);
        }
    }
}