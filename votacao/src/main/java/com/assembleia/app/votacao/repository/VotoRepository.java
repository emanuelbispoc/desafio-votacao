package com.assembleia.app.votacao.repository;

import com.assembleia.app.votacao.model.VotoPK;
import com.assembleia.app.votacao.model.VotoSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VotoRepository extends JpaRepository<VotoSessao, VotoPK> {
    boolean existsByIdSessaoIdAndIdAssociadoId(Long sessaoId, Long associadoId);
}
