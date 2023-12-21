package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/associados")
public class AssociadoController {
    private final AssociadoService associadoService;

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(associadoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AssociadoResponse> criar(@RequestBody @Valid AssociadoRequest request, UriComponentsBuilder uriBuilder) {
        AssociadoResponse associadoRegistrado = associadoService.salvar(request);
        return ResponseEntity.created(
                uriBuilder.path("/v1/associados/{id}").buildAndExpand(associadoRegistrado.id()).toUri()
        ).body(associadoRegistrado);
    }
}
