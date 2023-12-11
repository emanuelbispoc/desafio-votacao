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

    @OneToMany(mappedBy = "id.sessao", cascade = CascadeType.ALL)
    private List<VotoSessao> votos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private transient SessaoStatus status;

    private transient long votosSim;

    private transient long votosNao;

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

    public long getVotosSim() {
        return obterTotalVotosSim();
    }

    public long getVotosNao() {
        return obterTotalVotosNao();
    }

    public void adicionaVoto(VotoSessao voto) {
        voto.setSessao(this);
        votos.add(voto);
    }

    private long obterTotalVotosSim() {
        return votos.stream().filter(voto -> voto.getDecisao() == Voto.SIM).count();
    }

    private long obterTotalVotosNao() {
        return votos.stream().filter(voto -> voto.getDecisao() == Voto.NAO).count();
    }

    private void atualizarStatus() {
        if(dataFim.isAfter(LocalDateTime.now())) {
            status = SessaoStatus.EM_ANDAMENTO;
        }else {
            long votosSim = obterTotalVotosSim();
            long votosNao = obterTotalVotosNao();

            SessaoStatus votosVencedores = votosSim > votosNao ? SessaoStatus.PAUTA_APROVADA : SessaoStatus.PAUTA_REPROVADA;
            status = votosSim == votosNao ? SessaoStatus.EMPATE : votosVencedores;
        }
    }
}
