package com.assembleia.app.votacao.model;

import com.assembleia.app.votacao.enums.SessaoStatus;
import com.assembleia.app.votacao.enums.Voto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sessoes")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    @OneToMany(mappedBy = "id.sessao")
    private List<VotoSessao> votos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private transient SessaoStatus status;

    @OneToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    public Sessao(LocalDateTime dataInicio, LocalDateTime dataFim, Pauta pauta) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pauta = pauta;
    }

    public SessaoStatus getStatus() {
        atualizarStatus();
        return status;
    }

    public void atualizarStatus() {
        if(dataFim.isAfter(LocalDateTime.now())) {
            status = SessaoStatus.EM_ANDAMENTO;
        }else {
            long votosSim = votos.stream().filter(voto -> voto.getDecisao() == Voto.SIM).count();
            long votosNao = votos.stream().filter(voto -> voto.getDecisao() == Voto.NAO).count();

            SessaoStatus votosVencedores = votosSim > votosNao ? SessaoStatus.PAUTA_APROVADA : SessaoStatus.PAUTA_REPROVADA;
            status = votosSim == votosNao ? SessaoStatus.EMPATE : votosVencedores;
        }
    }
}
