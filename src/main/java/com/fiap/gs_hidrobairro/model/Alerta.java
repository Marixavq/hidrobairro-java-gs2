package com.fiap.gs_hidrobairro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA_HB")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Alerta → PontoMonitoramento: @ManyToOne
    @Column(name = "PONTO_ID", nullable = false)
    private Long pontoId;

    // Alerta → Leitura: @ManyToOne
    @Column(name = "leitura_id")
    private Long leituraId;

    @Column(name = "nivel_cm", precision = 8, scale = 2, nullable = false)
    private BigDecimal nivelCm;

    @Column(length = 20, nullable = false)
    private String severidade;

    @Column(length = 400, nullable = false)
    private String mensagem;

    @Column(length = 20, nullable = false)
    private String status = "ATIVO";

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "encerrado_em")
    private LocalDateTime encerradoEm;

    public Alerta() {
    }


}
