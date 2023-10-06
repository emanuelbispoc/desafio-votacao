package com.assembleia.app.votacao.exception;

public class UnprocessableEntityException extends RuntimeException{
    public UnprocessableEntityException(String mensagem) {
        super(mensagem);
    }
}
