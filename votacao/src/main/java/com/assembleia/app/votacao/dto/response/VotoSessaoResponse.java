package com.assembleia.app.votacao.dto.response;

import java.time.LocalDateTime;

public record VotoSessaoResponse(
        Long sessaoId,
        long totalVotosSim,
        long totalVotosNao,
        LocalDateTime dataTerminoSessao
) {}
