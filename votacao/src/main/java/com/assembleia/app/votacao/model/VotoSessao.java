package com.assembleia.app.votacao.model;

import com.assembleia.app.votacao.model.enums.Voto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "votos_sessao")
public class VotoSessao {
    @EmbeddedId
    private VotoPK id;

    @Enumerated(EnumType.STRING)
    private Voto decisao;

    public VotoSessao(Voto decisao, Associado associado) {
        id = new VotoPK();
        id.setAssociado(associado);
        this.decisao = decisao;
    }

    public void setSessao(Sessao sessao) {
        id.setSessao(sessao);
    }
}
