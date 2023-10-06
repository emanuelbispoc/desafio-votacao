package com.assembleia.app.votacao.exception;

public class DadoDuplicadoException extends RuntimeException{
    public DadoDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
