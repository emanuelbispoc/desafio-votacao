package com.assembleia.app.votacao.dto.response;

import java.time.LocalDate;

public record PautaResponse(
        Long id,
        String descricao,
        LocalDate dataCriacao,
        String relatorNome
) {}
