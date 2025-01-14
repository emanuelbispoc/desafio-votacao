package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoCriadaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;

public interface SessaoService {
    SessaoResponse buscarPorId(Long id);
    SessaoCriadaResponse salvar(SessaoRequest request);
    VotoSessaoResponse receberVoto(Long sessaoId , VotoRequest request);
}
