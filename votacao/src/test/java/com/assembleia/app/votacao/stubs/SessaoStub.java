package com.assembleia.app.votacao.stubs;

import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.model.Sessao;

import java.time.LocalDateTime;

public interface SessaoStub {
    static Sessao criarSessao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return new Sessao(1L, dataFim, dataFim, new Pauta());
    }

    static SessaoResponse criarSessaoResponse(Sessao sessao) {
        Pauta pauta = sessao.getPauta();
        PautaResponse pautaResponse = new PautaResponse(
                pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao(), "Relator nome"
        );
        return new SessaoResponse(
                sessao.getId(),
                sessao.getDataInicio(),
                sessao.getDataFim(),
                sessao.getVotosSim(),
                sessao.getVotosNao(),
                pautaResponse,
                sessao.getStatus()
        );
    }
}
