package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.model.Associado;

public interface AssociadoService {
    AssociadoResponse buscarPorId(Long id);
    Associado buscarPorCpf(String cpf);
    AssociadoResponse salvar(AssociadoRequest associado);
}
