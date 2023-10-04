package com.assembleia.app.votacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PautaRequest(
        @NotBlank(message = "Descrição é obrigatória.")
        String descricao,
        @NotNull(message = "Campo obrigatório.")
        Long associadoId
) {}
