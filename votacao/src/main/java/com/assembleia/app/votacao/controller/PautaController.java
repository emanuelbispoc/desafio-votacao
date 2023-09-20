package com.assembleia.app.votacao.controller;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pauta")
public class PautaController {
    private final PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaResponse> criar(@RequestBody PautaRequest pautaRequest) {
        return ResponseEntity.ok(pautaService.salvar(pautaRequest));
    }
}
