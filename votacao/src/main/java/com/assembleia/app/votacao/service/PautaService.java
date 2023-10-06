package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.model.Pauta;

public interface PautaService {
    PautaResponse salvar(PautaRequest pautaRequest);
    Pauta validaPorId(Long id);
}
