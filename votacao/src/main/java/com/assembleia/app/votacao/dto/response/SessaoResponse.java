package com.assembleia.app.votacao.dto.response;

import com.assembleia.app.votacao.enums.SessaoStatus;

import java.time.LocalDateTime;

public record SessaoResponse (
        Long id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        long votosSim,
        long votosNao,
        PautaResponse pauta,
        SessaoStatus status
) {}
