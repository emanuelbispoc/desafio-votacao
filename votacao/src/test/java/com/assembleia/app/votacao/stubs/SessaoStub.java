package com.assembleia.app.votacao.stubs;

import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.model.Sessao;
import com.assembleia.app.votacao.model.VotoSessao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessaoStub {
    public static Sessao criarSessao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return new Sessao(1L, dataFim, dataFim, new Pauta());
    }

    public static Sessao criarSessaoComVotoPadrao(LocalDateTime dataInicio, LocalDateTime dataFim, VotoSessao voto) {
        return new Sessao(1L, dataInicio, dataFim, new ArrayList<>(List.of(voto)), new Pauta());
    }

    public static SessaoResponse criarSessaoResponse(Sessao sessao) {
        Pauta pauta = sessao.getPauta();
        PautaResponse pautaResponse = new PautaResponse(
                pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao(), "Relator nome"
        );
        return new SessaoResponse(
                sessao.getId(),
                sessao.getDataInicio(),
                sessao.getDataFim(),
                sessao.obterTotalVotosSim(),
                sessao.obterTotalVotosNao(),
                pautaResponse,
                sessao.calcularStatus(),
                sessao.obterResultado()
        );
    }
}
