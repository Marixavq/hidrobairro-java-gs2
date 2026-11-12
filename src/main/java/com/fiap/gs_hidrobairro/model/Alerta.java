package com.fiap.gs_hidrobairro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA_HB")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_id", nullable = false)
    @Schema(
            description = "Alerta associado ao ponto monitorado",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private PontoMonitoramento ponto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leitura_id")
    @Schema(
            description = "Alerta associado a leitura",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Leitura leitura;

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
