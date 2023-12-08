package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
import com.assembleia.app.votacao.model.Sessao;

public interface SessaoService {
    Sessao buscarPorId(Long id);
    SessaoResponse salvar(SessaoRequest request);
    VotoSessaoResponse receberVoto(Long sessaoId , VotoRequest request);
}
