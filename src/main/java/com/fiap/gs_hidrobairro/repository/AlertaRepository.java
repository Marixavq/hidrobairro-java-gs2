package com.fiap.gs_hidrobairro.repository;

import com.fiap.gs_hidrobairro.model.Alerta;
import com.fiap.gs_hidrobairro.model.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    // Verifica se já existe um alerta ativo para o ponto
    boolean existsByPontoIdAndStatus(Long pontoId, StatusAlerta status);

    // Busca o alerta ativo do ponto
    Optional<Alerta> findByPontoIdAndStatus(Long pontoId, StatusAlerta status);

    // Lista todos os alertas ativos
    List<Alerta> findByStatus(StatusAlerta status);
}
