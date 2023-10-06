package com.assembleia.app.votacao.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class VotoPK {
    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    private Associado associado;
}
