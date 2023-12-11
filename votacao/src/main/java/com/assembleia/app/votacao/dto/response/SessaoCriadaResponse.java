package com.assembleia.app.votacao.dto.response;

import java.time.LocalDateTime;

public record SessaoCriadaResponse(
        Long id,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        PautaResponse pauta
) {}
