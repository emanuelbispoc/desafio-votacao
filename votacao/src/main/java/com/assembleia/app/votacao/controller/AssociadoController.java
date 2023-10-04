package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/associado")
public class AssociadoController {
    private final AssociadoService associadoService;

    @GetMapping("/{associadoId}")
    public ResponseEntity<AssociadoResponse> buscar(@PathVariable Long associadoId) {
        return ResponseEntity.ok(associadoService.buscarPorId(associadoId));
    }

    @PostMapping
    public ResponseEntity<AssociadoResponse> criar(@RequestBody @Valid AssociadoRequest request) {
        return ResponseEntity.ok(associadoService.salvar(request));
    }
}
