package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.client.CpfClient;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.model.enums.SituacaoCpf;
import com.assembleia.app.votacao.service.CpfService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CpfServiceImpl implements CpfService {
    private final CpfClient cpfClient;

    @Override
    public void verificarSeCpfExiste(String cpf) {
        try {
            cpfClient.verificarSeExiste(cpf);
        }catch (FeignException.FeignClientException e){
            throw new NotFoundException("CPF inválido");
        }
    }

    @Override
    public void verificarSeSituacaoEstaRegular(String cpf) {
        if(cpfClient.buscar(cpf).status() == SituacaoCpf.IRREGULAR) {
            throw new UnprocessableEntityException("UNABLE_TO_VOTE");
        }
    }
}
