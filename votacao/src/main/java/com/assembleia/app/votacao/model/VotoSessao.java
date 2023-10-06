package com.assembleia.app.votacao.model;

import com.assembleia.app.votacao.enums.Voto;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private Voto decisao;
}
