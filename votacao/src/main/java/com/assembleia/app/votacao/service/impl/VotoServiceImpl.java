package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.repository.VotoRepository;
import com.assembleia.app.votacao.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VotoServiceImpl implements VotoService {
    private final VotoRepository repository;

    @Override
    public void verificaSeVotoJaExiste(Long sessaoId, Long associadoId) {
        if(repository.existsByIdSessaoIdAndIdAssociadoId(sessaoId, associadoId)) {
            throw new UnprocessableEntityException("Voto já registrado.");
        }
    }
}
