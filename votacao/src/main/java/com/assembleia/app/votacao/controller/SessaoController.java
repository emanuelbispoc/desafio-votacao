package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/sessoes")
public class SessaoController {
    private final SessaoService sessaoService;

    @PostMapping
    public ResponseEntity<SessaoResponse> criar(@RequestBody SessaoRequest request, UriComponentsBuilder uriBuilder) {
        SessaoResponse sessaoSalva = sessaoService.salvar(request);
        return ResponseEntity.created(
                uriBuilder.path("/v1/sessoes/{id}").buildAndExpand(sessaoSalva.id()).toUri()
        ).body(sessaoSalva);
    }
}
