package com.assembleia.app.votacao.stubs;

import com.assembleia.app.votacao.model.Associado;

public interface AssociadoStub {
    static Associado criarAssociado(Long id, String nome, String cpf) {
        return new Associado(id, nome, cpf);
    }
}
