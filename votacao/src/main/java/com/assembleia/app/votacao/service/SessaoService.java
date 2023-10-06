package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;

public interface SessaoService {
    SessaoResponse salvar(SessaoRequest request);
}
