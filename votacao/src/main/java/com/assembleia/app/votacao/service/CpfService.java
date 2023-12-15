package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.response.CpfValidacaoResponse;

public interface CpfService {
    void validacao(String cpf);
    CpfValidacaoResponse verificaSituacao(String cpf);
}
