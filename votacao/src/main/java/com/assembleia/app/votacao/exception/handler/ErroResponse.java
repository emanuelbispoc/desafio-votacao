package com.assembleia.app.votacao.exception.handler;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErroResponse {
    private String descricao;
    private LocalDateTime dataHora = LocalDateTime.now();

    public ErroResponse(String descricao){
        this.descricao = descricao;
    }
}
