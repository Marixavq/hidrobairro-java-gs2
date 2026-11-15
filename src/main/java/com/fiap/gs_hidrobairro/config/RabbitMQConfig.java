
package com.fiap.gs_hidrobairro.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.alertas}")
    private String alertaExchange;

    @Value("${rabbitmq.queue.alertas}")
    private String alertaQueue;

    @Value("${rabbitmq.routing-key.alerta-criado}")
    private String routingKey;

    @Bean
    public DirectExchange alertaExchange() {
        return new DirectExchange(alertaExchange);
    }

    @Bean
    public Queue alertaQueue() {
        return QueueBuilder.durable(alertaQueue).build();
    }

    @Bean
    public Binding alertaBinding() {
        return BindingBuilder
                .bind(alertaQueue())
                .to(alertaExchange())
                .with(routingKey);
    }

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
