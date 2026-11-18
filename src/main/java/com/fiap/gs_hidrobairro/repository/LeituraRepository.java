package com.fiap.gs_hidrobairro.repository;

import com.fiap.gs_hidrobairro.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    Optional<Leitura> findTopByPontoIdOrderByColetadoEmDesc(Long pontoId);
}