package com.assembleia.app.votacao.repository;

import com.assembleia.app.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    boolean existsByVotosIdSessaoIdAndVotosIdAssociadoId(Long sessaoId, Long associadoId);
}
