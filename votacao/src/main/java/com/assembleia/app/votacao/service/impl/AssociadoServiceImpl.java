package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.exception.DadoDuplicadoException;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.AssociadoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.repository.AssociadoRepository;
import com.assembleia.app.votacao.service.AssociadoService;
import com.assembleia.app.votacao.service.CpfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AssociadoServiceImpl implements AssociadoService {
    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;
    private final CpfService cpfService;

    @Override
    public AssociadoResponse buscarPorId(Long id) {
        Associado associado = associadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Associado não encontrado."));

        return associadoMapper.modelToResponse(associado);
    }

    @Override
    public Associado buscarPorCpf(String cpf) {
        return associadoRepository.findByCpf(cpf)
                .orElseThrow(() -> new UnprocessableEntityException("Associado não encontrado."));
    }

    @Override
    public AssociadoResponse salvar(AssociadoRequest associadoRequest) {
        cpfService.validacao(associadoRequest.cpf());
        validarSeJaExistePorCpf(associadoRequest.cpf());
        Associado associado = associadoMapper.requestToModel(associadoRequest);
        return associadoMapper.modelToResponse(associadoRepository.save(associado));
    }

    private void validarSeJaExistePorCpf(String cpf) {
        if (associadoRepository.existsByCpf(cpf))
            throw new DadoDuplicadoException("CPF informado já cadastrado.");
    }
}
