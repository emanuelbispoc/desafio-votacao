package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.PautaMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.repository.AssociadoRepository;
import com.assembleia.app.votacao.repository.PautaRepository;
import com.assembleia.app.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PautaServiceImpl implements PautaService {
    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;
    private final AssociadoRepository associadoRepository;

    @Override
    public PautaResponse salvar(PautaRequest pautaRequest) {
        Associado associado = associadoRepository.findById(pautaRequest.associadoId())
                .orElseThrow(() -> new NotFoundException("Associado não encontrado."));

        Pauta pauta = pautaMapper.requestToModel(pautaRequest);
        pauta.setRelator(associado);

        return pautaMapper.modelToResponse(pautaRepository.save(pauta));
    }

    @Override
    public Pauta validaPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new UnprocessableEntityException("Pauta não encontrada."));
    }
}
