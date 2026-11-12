package com.fiap.gs_hidrobairro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA_HB")
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_id", nullable = false)
    @Schema(
            description = "Leitura associada ao ponto monitorado",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private PontoMonitoramento ponto;

    @Column(name = "nivel_cm", precision = 8, scale = 2, nullable = false)
    private BigDecimal nivelCm;

    @Column(name = "coletado_em", insertable = false, updatable = false)
    private LocalDateTime coletadoEm;

    @Column(length = 20, nullable = false)
    private String origem;

    public Leitura() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PontoMonitoramento getPonto() {
        return ponto;
    }

    public void setPonto(PontoMonitoramento ponto) {
        this.ponto = ponto;
    }

    public BigDecimal getNivelCm() {
        return nivelCm;
    }

    public void setNivelCm(BigDecimal nivelCm) {
        this.nivelCm = nivelCm;
    }

    public LocalDateTime getColetadoEm() {
        return coletadoEm;
    }

    public void setColetadoEm(LocalDateTime coletadoEm) {
        this.coletadoEm = coletadoEm;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
