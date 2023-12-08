package com.assembleia.app.votacao.dto.request;

import com.assembleia.app.votacao.enums.Voto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoRequest(
        @NotBlank(message = "Campo obrigatório.")
        String associadoCpf,
        @NotNull(message = "Campo obrigatório.")
        Voto voto
) {}
