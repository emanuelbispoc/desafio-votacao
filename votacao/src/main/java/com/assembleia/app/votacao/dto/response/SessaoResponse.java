package com.assembleia.app.votacao.dto.response;

import com.assembleia.app.votacao.model.VotoSessao;

import java.time.LocalDateTime;
import java.util.List;

public record SessaoResponse(
        Long id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        List<VotoSessao> votos,
        PautaResponse pauta
) {}
