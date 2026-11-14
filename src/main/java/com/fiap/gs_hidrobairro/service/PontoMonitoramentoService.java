package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.dto.request.PontoMonitoramentoRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.PontoMonitoramentoResponseDTO;
import com.fiap.gs_hidrobairro.exception.RecursoNaoEncontradoException;
import com.fiap.gs_hidrobairro.exception.RegraNegocioException;
import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import com.fiap.gs_hidrobairro.repository.PontoMonitoramentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PontoMonitoramentoService {

    private final PontoMonitoramentoRepository pontoRepository;

    @Autowired
    public PontoMonitoramentoService(PontoMonitoramentoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    @Transactional
    public PontoMonitoramentoResponseDTO salvar(PontoMonitoramentoRequestDTO requestDTO) {

        PontoMonitoramento ponto = new PontoMonitoramento();
        ponto.setNome(requestDTO.nome());
        ponto.setSubprefeitura(requestDTO.subprefeitura());
        ponto.setBairro(requestDTO.bairro());
        ponto.setZona(requestDTO.zona());
        ponto.setLatitude(requestDTO.latitude());
        ponto.setLongitude(requestDTO.longitude());
        ponto.setLimiarAlertaCm(requestDTO.limiarAlertaCm());
        ponto.setCriadoEm(LocalDateTime.now());

        PontoMonitoramento pontoSalvo = pontoRepository.save(ponto);

        return PontoMonitoramentoResponseDTO.fromEntity(pontoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<PontoMonitoramentoResponseDTO> listarTodos(Pageable pageable) {
        Page<PontoMonitoramento> pontos = pontoRepository.findAll(pageable);
        return pontos.map(PontoMonitoramentoResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public PontoMonitoramentoResponseDTO buscarPorId(Long id) {
        PontoMonitoramento ponto = pontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de monitoramento não encontrado com ID: " + id));
        return PontoMonitoramentoResponseDTO.fromEntity(ponto);
    }

    @Transactional
    public PontoMonitoramentoResponseDTO atualizar(Long id, PontoMonitoramentoRequestDTO requestDTO) {
        PontoMonitoramento pontoExistente = pontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de monitoramento não encontrado com ID: " + id));

        if (pontoExistente.getAtivo() != 'S') {
            throw new RegraNegocioException("Não é possível atualizar um ponto de monitoramento desativado");
        }

        pontoExistente.setNome(requestDTO.nome());
        pontoExistente.setSubprefeitura(requestDTO.subprefeitura());
        pontoExistente.setBairro(requestDTO.bairro());
        pontoExistente.setZona(requestDTO.zona());
        pontoExistente.setLatitude(requestDTO.latitude());
        pontoExistente.setLongitude(requestDTO.longitude());
        pontoExistente.setLimiarAlertaCm(requestDTO.limiarAlertaCm());

        PontoMonitoramento pontoAtualizado = pontoRepository.save(pontoExistente);

        return PontoMonitoramentoResponseDTO.fromEntity(pontoAtualizado);
    }

    // Ponto de monitoramento não pode ser excluído literalmente, apenas desativado (pelo GESTOR)
    @Transactional
    public PontoMonitoramentoResponseDTO desativar(Long id) {
        PontoMonitoramento ponto = pontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de monitoramento não encontrado com ID: " + id));

        if (ponto.getAtivo() == 'N') {
            throw new RegraNegocioException("O ponto de monitoramento já está desativado");
        }

        ponto.setAtivo('N');

        return PontoMonitoramentoResponseDTO.fromEntity(ponto);
    }

    // Ponto de monitoramento precisa ser reativado pelo GESTOR
    @Transactional
    public PontoMonitoramentoResponseDTO reativar(Long id) {
        PontoMonitoramento ponto = pontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de monitoramento não encontrado com ID: " + id));

        if (ponto.getAtivo() == 'S') {
            throw new RegraNegocioException("O ponto de monitoramento já está ativo");
        }

        ponto.setAtivo('S');

        return PontoMonitoramentoResponseDTO.fromEntity(ponto);
    }


}
