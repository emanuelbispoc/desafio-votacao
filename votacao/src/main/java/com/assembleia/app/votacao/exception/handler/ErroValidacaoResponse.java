package com.assembleia.app.votacao.exception.handler;

import lombok.Getter;

import java.util.List;

@Getter
public class ErroValidacaoResponse extends ErroResponse {
    private List<ErroCampo> campos;

    public ErroValidacaoResponse(String descricao, List<ErroCampo> campos) {
        super(descricao);
        this.campos = campos;
    }

    @Getter
    public static class ErroCampo {
        private String nome;
        private String mensagem;

        public ErroCampo(String nome, String mensagem) {
            this.nome = nome;
            this.mensagem = mensagem;
        }
    }
}
