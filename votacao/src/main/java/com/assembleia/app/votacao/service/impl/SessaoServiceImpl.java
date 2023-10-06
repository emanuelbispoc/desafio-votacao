package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.mapper.SessaoMapper;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.model.Sessao;
import com.assembleia.app.votacao.repository.SessaoRepository;
import com.assembleia.app.votacao.service.PautaService;
import com.assembleia.app.votacao.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SessaoServiceImpl implements SessaoService {
    private final PautaService pautaService;
    private final SessaoRepository repository;
    private final SessaoMapper mapper;

    @Override
    public SessaoResponse salvar(SessaoRequest request) {
        Pauta pautaEncontrada = pautaService.validaPorId(request.pautaId());

        LocalDateTime dataAtual = LocalDateTime.now();
        Sessao novaSessao = new Sessao(
                dataAtual, dataAtual.plusMinutes(request.duracao()), pautaEncontrada
        );

        return mapper.modelToResponse(repository.save(novaSessao));
    }
}
