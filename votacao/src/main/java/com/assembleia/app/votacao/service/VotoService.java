package com.assembleia.app.votacao.service;

public interface VotoService {
    void verificaSeVotoJaExiste(Long sessaoId, Long associadoId);
}
