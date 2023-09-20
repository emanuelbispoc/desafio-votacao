package com.assembleia.app.votacao.dto.request;

public record PautaRequest(
        String descricao,
        Long associadoId
) {}
