package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.mapper.AssociadoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.repository.AssociadoRepository;
import com.assembleia.app.votacao.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AssociadoServiceImpl implements AssociadoService {
    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;

    @Override
    public AssociadoResponse buscarPorId(Long id) {
        Associado associado = associadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Associado não encontrado."));

        return associadoMapper.modelToResponse(associado);
    }

    @Override
    public AssociadoResponse salvar(AssociadoRequest associadoRequest) {
        Associado associado = associadoMapper.requestToModel(associadoRequest);
        return associadoMapper.modelToResponse(associadoRepository.save(associado));
    }
}
