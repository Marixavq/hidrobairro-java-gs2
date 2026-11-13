package com.fiap.gs_hidrobairro.service;

import com.fiap.gs_hidrobairro.dto.request.LeituraRequestDTO;
import com.fiap.gs_hidrobairro.dto.response.LeituraResponseDTO;
import com.fiap.gs_hidrobairro.exception.RecursoNaoEncontradoException;
import com.fiap.gs_hidrobairro.exception.RegraNegocioException;
import com.fiap.gs_hidrobairro.model.Leitura;
import com.fiap.gs_hidrobairro.model.PontoMonitoramento;
import com.fiap.gs_hidrobairro.repository.LeituraRepository;
import com.fiap.gs_hidrobairro.repository.PontoMonitoramentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeituraService {

    private final LeituraRepository leituraRepository;
    private final PontoMonitoramentoRepository pontoMonitoramentoRepository;
    private final AlertaService alertaService;

    @Autowired
    public LeituraService(LeituraRepository leituraRepository, PontoMonitoramentoRepository pontoMonitoramentoRepository, AlertaService alertaService) {
        this.leituraRepository = leituraRepository;
        this.pontoMonitoramentoRepository = pontoMonitoramentoRepository;
        this.alertaService = alertaService;
    }

    @Transactional
    public LeituraResponseDTO salvar(LeituraRequestDTO requestDTO) {
        PontoMonitoramento ponto = pontoMonitoramentoRepository.findById(requestDTO.pontoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de monitoramento não encontrado"));

        if (ponto.getAtivo() != 'S') {
            throw new RegraNegocioException("Não é possível registrar leituras em um ponto desativado");
        }

        Leitura leitura = new Leitura();
        leitura.setPonto(ponto);
        leitura.setNivelCm(requestDTO.nivelCm());
        leitura.setOrigem(requestDTO.origem());

        Leitura leituraSalva = leituraRepository.save(leitura);

        alertaService.avaliarLeitura(leituraSalva);

        return LeituraResponseDTO.fromEntity(leituraSalva);
    }

    @Transactional(readOnly = true)
    public Page<LeituraResponseDTO> listarTodos(Pageable pageable) {
        Page<Leitura> leituras = leituraRepository.findAll(pageable);
        return leituras.map(LeituraResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public LeituraResponseDTO buscarPorId(Long id) {
        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leitura não encontrada com ID: " + id));
        return LeituraResponseDTO.fromEntity(leitura);
    }


}
