package com.fiap.gs_hidrobairro.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PONTO_MONITORAMENTO_HB")
public class PontoMonitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    private String nome;

    @Column(length = 80, nullable = false)
    private String subprefeitura;

    @Column(length = 80, nullable = false)
    private String bairro;

    @Column(length = 20, nullable = false)
    private String zona;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "limiar_alerta_cm", precision = 8, scale = 2, nullable = false)
    private BigDecimal limiarAlertaCm;

    @Column(length = 1, nullable = false)
    private String ativo;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    public PontoMonitoramento() {
    }

    // Getters e Setters
}
