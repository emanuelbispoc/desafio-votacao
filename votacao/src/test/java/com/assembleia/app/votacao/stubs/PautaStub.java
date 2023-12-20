package com.assembleia.app.votacao.stubs;

import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Pauta;

import java.time.LocalDate;

public interface PautaStub {
    static Pauta criarPauta(Long id, String descricao, Associado associado) {
        return new Pauta(id, descricao, LocalDate.now(), associado);
    }
    static PautaResponse criarPautaResponse(Pauta pauta) {
        return new PautaResponse(
                pauta.getId(),
                pauta.getDescricao(),
                pauta.getDataCriacao(),
                pauta.getRelator().getNome()
        );
    }
}