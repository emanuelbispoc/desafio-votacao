package com.assembleia.app.votacao.client;

import com.assembleia.app.votacao.dto.response.CpfValidacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "validadorCpf", url = "${cpfvalidador.api.url}")
public interface CpfClient {
    @GetMapping(value = "/{cpf}")
    void valida(@PathVariable String cpf);

    @GetMapping(value = "/{cpf}/situacao")
    CpfValidacaoResponse buscar(@PathVariable String cpf);
}
