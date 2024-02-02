package com.assembleia.app.votacao.service;

public interface CpfService {
    void verificarSeCpfExiste(String cpf);
    void verificarSeSituacaoEstaRegular(String cpf);
}
