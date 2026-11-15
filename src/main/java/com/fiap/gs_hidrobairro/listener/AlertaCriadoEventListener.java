package com.fiap.gs_hidrobairro.listener;

import com.fiap.gs_hidrobairro.event.AlertaCriadoEvent;
import com.fiap.gs_hidrobairro.publisher.AlertaEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AlertaCriadoEventListener {

    private final AlertaEventPublisher alertaEventPublisher;

    public AlertaCriadoEventListener(AlertaEventPublisher alertaEventPublisher) {
        this.alertaEventPublisher = alertaEventPublisher;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoCriarAlerta(AlertaCriadoEvent evento) {
        alertaEventPublisher.publicarAlertaCriado(evento);
    }
}

    /*
- @TransactionalEventListener: registra um metodo que reage a um evento respeitando o ciclo da transação.
- TransactionPhase.AFTER_COMMIT: determina que ele só será executado depois da confirmação da transação.
     */