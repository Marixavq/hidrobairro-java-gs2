package com.fiap.gs_hidrobairro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA_HB")
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Leitura → PontoMonitoramento: @ManyToOne
    @Column(name = "PONTO_ID", nullable = false)
    private Long pontoId;

    @Column(name = "NIVEL_CM", precision = 8, scale = 2, nullable = false)
    private BigDecimal nivelCm;

    @Column(name = "COLETADO_EM", insertable = false, updatable = false)
    private LocalDateTime coletadoEm;

    @Column(name = "ORIGEM", length = 20, nullable = false)
    private String origem;

    public Leitura() {
    }

    // Getters e Setters
}
