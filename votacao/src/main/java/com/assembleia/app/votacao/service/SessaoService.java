package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.model.Sessao;

public interface SessaoService {
    SessaoResponse salvar(SessaoRequest request);
}
