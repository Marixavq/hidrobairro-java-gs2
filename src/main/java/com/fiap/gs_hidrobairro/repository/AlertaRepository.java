package com.fiap.gs_hidrobairro.repository;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.StatusAlerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Optional<Alerta> findByPontoIdAndStatus(Long pontoId, StatusAlerta status);

    Page<Alerta> findByStatus(StatusAlerta status, Pageable pageable);
}
