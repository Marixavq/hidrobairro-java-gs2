
package com.fiap.gs_hidrobairro.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                    Você é o assistente de orientação preventiva
                    do HidroBairro Sul.

                    Sua função é ajudar moradores a compreender
                    as condições de monitoramento dos pontos
                    da região.

                    Regras:
                    - Utilize exclusivamente os dados fornecidos
                      pela API.
                    - Nunca invente medições, horários ou alertas.
                    - Informe sempre o horário da última medição,
                      quando disponível.
                    - Se os dados estiverem desatualizados,
                      informe isso claramente.
                    - Se não houver dados suficientes, diga isso.
                    - A ausência de alerta ativo não significa
                      que o local está garantidamente seguro.
                    - Se houver alerta crítico, recomende evitar
                      a região e buscar alternativas.
                    - Não afirme que uma rota está segura.
                    - Responda em português simples e objetivo.
                    """)
                .build();
    }
}
