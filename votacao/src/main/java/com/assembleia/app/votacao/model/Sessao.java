package com.assembleia.app.votacao.model;

import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.model.enums.SessaoResultado;
import com.assembleia.app.votacao.model.enums.SessaoStatus;
import com.assembleia.app.votacao.model.enums.Voto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
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

    @OneToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    public Sessao(LocalDateTime dataInicio, LocalDateTime dataFim, Pauta pauta) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pauta = pauta;
    }

    public Sessao(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, List<VotoSessao> votos, Pauta pauta) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.votos = votos;
        this.pauta = pauta;
    }

    public Sessao(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, Pauta pauta) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pauta = pauta;
    }

    public void adicionarVoto(VotoSessao voto) {
        validarSeEstaEmAndamento();
        voto.setSessao(this);
        votos.add(voto);
    }

    public long obterTotalVotosSim() {
        return votos.stream().filter(voto -> voto.getDecisao() == Voto.SIM).count();
    }

    public long obterTotalVotosNao() {
        return votos.stream().filter(voto -> voto.getDecisao() == Voto.NAO).count();
    }

    public SessaoStatus calcularStatus() {
        if (dataFim.isAfter(LocalDateTime.now())) {
            return SessaoStatus.EM_ANDAMENTO;
        }

        return SessaoStatus.FINALIZADO;
    }

    public SessaoResultado obterResultado() {
        if (estaEmAndamento()) {
            return SessaoResultado.VOTACAO_EM_ANDAMENTO;
        }

        long votosSim = obterTotalVotosSim();
        long votosNao = obterTotalVotosNao();

        SessaoResultado votosVencedores = votosSim > votosNao
                ? SessaoResultado.PAUTA_APROVADA
                : SessaoResultado.PAUTA_REPROVADA;

        return votosSim == votosNao ? SessaoResultado.EMPATE : votosVencedores;
    }

    public void validarSeEstaEmAndamento() {
        if(!estaEmAndamento())
            throw new UnprocessableEntityException("Sessão não está em andamento.");
    }

    private boolean estaEmAndamento() {
        return calcularStatus() == SessaoStatus.EM_ANDAMENTO;
    }
}
