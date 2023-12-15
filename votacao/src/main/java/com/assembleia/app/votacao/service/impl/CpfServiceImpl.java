package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.client.CpfClient;
import com.assembleia.app.votacao.dto.response.CpfValidacaoResponse;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.service.CpfService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CpfServiceImpl implements CpfService {
    private final CpfClient cpfClient;

    @Override
    public void validacao(String cpf) {
        try {
            cpfClient.valida(cpf);
        }catch (FeignException.FeignClientException e){
            throw new NotFoundException("CPF inválido");
        }
    }

    @Override
    public CpfValidacaoResponse verificaSituacao(String cpf) {
        return cpfClient.buscar(cpf);
    }
}
