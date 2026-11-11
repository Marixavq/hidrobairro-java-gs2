package com.fiap.gs_hidrobairro.repository;

import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoMonitoramentoRepository extends JpaRepository<PontoMonitoramento, Long> {
}
