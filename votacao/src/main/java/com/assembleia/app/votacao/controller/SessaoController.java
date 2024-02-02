package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoCriadaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
import com.assembleia.app.votacao.service.SessaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/sessoes")
public class SessaoController {
    private final SessaoService sessaoService;

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponse> obter(@PathVariable Long id) {
        return ResponseEntity.ok(sessaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SessaoCriadaResponse> criar(@RequestBody @Valid SessaoRequest request, UriComponentsBuilder uriBuilder) {
        SessaoCriadaResponse sessaoRegistrada = sessaoService.salvar(request);
        return ResponseEntity.created(
                uriBuilder.path("/v1/sessoes/{id}").buildAndExpand(sessaoRegistrada.id()).toUri()
        ).body(sessaoRegistrada);
    }

    @PostMapping("/{id}/votos")
    public ResponseEntity<VotoSessaoResponse> registraVoto(@PathVariable Long id, @RequestBody @Valid VotoRequest request) {
        return ResponseEntity.ok(sessaoService.receberVoto(id, request));
    }
}
