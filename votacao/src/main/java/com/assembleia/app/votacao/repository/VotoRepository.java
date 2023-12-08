package com.assembleia.app.votacao.repository;

import com.assembleia.app.votacao.model.VotoPK;
import com.assembleia.app.votacao.model.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<VotoSessao, VotoPK> {
    boolean existsByIdSessaoIdAndIdAssociadoId(Long sessaoId, Long associadoId);
}
