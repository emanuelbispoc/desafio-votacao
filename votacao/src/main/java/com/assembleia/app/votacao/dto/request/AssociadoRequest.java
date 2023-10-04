package com.assembleia.app.votacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssociadoRequest(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,
        @NotBlank(message = "CPF é obrigatório.")
        @Size(min = 11, max = 11, message = "CPF deve conter 11 digitos.")
        String cpf
) {
}
