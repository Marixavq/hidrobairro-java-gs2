package com.fiap.gs_hidrobairro.repository;

import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PontoMonitoramentoRepository extends JpaRepository<PontoMonitoramento, Long> {

    Optional<PontoMonitoramento> findById(Long id);

}
