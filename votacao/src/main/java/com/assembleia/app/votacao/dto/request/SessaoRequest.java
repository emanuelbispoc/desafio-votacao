package com.assembleia.app.votacao.dto.request;

import jakarta.validation.constraints.NotNull;

public record SessaoRequest(
        @NotNull(message = "Pauta é obrigatória")
        Long pautaId,
        Long duracao
) {
    private static final Integer DURACAO_PADRAO_MIN = 1;

    @Override
    public Long duracao() {
        return duracao != null ? duracao : DURACAO_PADRAO_MIN ;
    }
}
