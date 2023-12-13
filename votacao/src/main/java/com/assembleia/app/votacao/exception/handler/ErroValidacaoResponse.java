package com.assembleia.app.votacao.exception.handler;

import lombok.Getter;

import java.util.List;

@Getter
public class ErroValidacaoResponse extends ErroResponse {
    private final List<ErroCampo> campos;

    public ErroValidacaoResponse(String descricao, List<ErroCampo> campos) {
        super(descricao);
        this.campos = campos;
    }

    public record ErroCampo(String nome, String mensagem) {
    }
}
