package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pautas")
public class PautaController {
    private final PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaResponse> criar(@RequestBody @Valid PautaRequest pautaRequest, UriComponentsBuilder uriBuilder) {
        PautaResponse pautaRegistrada = pautaService.salvar(pautaRequest);
        return ResponseEntity.created(
                uriBuilder.path("/v1/pautas/{id}").buildAndExpand(pautaRegistrada.id()).toUri()
        ).body(pautaRegistrada);
    }
}
