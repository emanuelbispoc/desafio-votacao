package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.response.CpfValidacaoResponse;

public interface CpfService {
    void verificarSeCpfExiste(String cpf);
    void verificarSeSituacaoEstaRegular(String cpf);
}
