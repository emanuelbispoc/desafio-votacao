package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.AssociadoMapper;
import com.assembleia.app.votacao.mapper.PautaMapper;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.repository.PautaRepository;
import com.assembleia.app.votacao.service.AssociadoService;
import com.assembleia.app.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PautaServiceImpl implements PautaService {
    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;
    private final AssociadoMapper associadoMapper;
    private final AssociadoService associadoService;

    @Override
    public PautaResponse salvar(PautaRequest pautaRequest) {
        AssociadoResponse associado = associadoService
                .buscarPorId(pautaRequest.associadoId());

        Pauta pauta = pautaMapper.requestToModel(pautaRequest);
        pauta.setRelator(associadoMapper.responseToModel(associado));

        return pautaMapper.modelToResponse(pautaRepository.save(pauta));
    }

    @Override
    public Pauta validaPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new UnprocessableEntityException("Pauta não encontrada."));
    }
}
