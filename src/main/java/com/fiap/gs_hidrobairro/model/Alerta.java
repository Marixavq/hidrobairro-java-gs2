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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeveridadeAlerta severidade;

    @Column(length = 400, nullable = false)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlerta status = StatusAlerta.ATIVO;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "encerrado_em")
    private LocalDateTime encerradoEm;

    public Alerta() {
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

    public Leitura getLeitura() {
        return leitura;
    }

    public void setLeitura(Leitura leitura) {
        this.leitura = leitura;
    }

    public BigDecimal getNivelCm() {
        return nivelCm;
    }

    public void setNivelCm(BigDecimal nivelCm) {
        this.nivelCm = nivelCm;
    }

    public SeveridadeAlerta getSeveridade() {
        return severidade;
    }

    public void setSeveridade(SeveridadeAlerta severidade) {
        this.severidade = severidade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public StatusAlerta getStatus() {
        return status;
    }

    public void setStatus(StatusAlerta status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getEncerradoEm() {
        return encerradoEm;
    }

    public void setEncerradoEm(LocalDateTime encerradoEm) {
        this.encerradoEm = encerradoEm;
    }
}
