package com.assembleia.app.votacao.repository;

import com.assembleia.app.votacao.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {
    Optional<Associado> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
