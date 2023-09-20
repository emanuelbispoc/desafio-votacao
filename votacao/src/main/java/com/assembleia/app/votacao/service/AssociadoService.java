package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;

public interface AssociadoService {
    AssociadoResponse buscarPorId(Long id);
    AssociadoResponse salvar(AssociadoRequest associado);
}
