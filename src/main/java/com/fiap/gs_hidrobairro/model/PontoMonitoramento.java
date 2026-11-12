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

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Zona zona;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "limiar_alerta_cm", precision = 8, scale = 2, nullable = false)
    private BigDecimal limiarAlertaCm;

    @Column //(length = 1, nullable = false)
    private char ativo = 'S';

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    public PontoMonitoramento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSubprefeitura() {
        return subprefeitura;
    }

    public void setSubprefeitura(String subprefeitura) {
        this.subprefeitura = subprefeitura;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLimiarAlertaCm() {
        return limiarAlertaCm;
    }

    public void setLimiarAlertaCm(BigDecimal limiarAlertaCm) {
        this.limiarAlertaCm = limiarAlertaCm;
    }

    public char getAtivo() {
        return ativo;
    }

    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
