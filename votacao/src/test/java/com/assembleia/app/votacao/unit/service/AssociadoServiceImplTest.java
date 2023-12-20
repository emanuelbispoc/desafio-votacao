package com.assembleia.app.votacao.unit.service;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.exception.DadoDuplicadoException;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.mapper.AssociadoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.repository.AssociadoRepository;
import com.assembleia.app.votacao.service.impl.AssociadoServiceImpl;
import com.assembleia.app.votacao.service.impl.CpfServiceImpl;
import com.assembleia.app.votacao.stubs.AssociadoStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceImplTest {
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private CpfServiceImpl cpfService;
    @Mock
    private AssociadoMapper associadoMapper;

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    Associado associado = AssociadoStub.criarAssociado(1L, "Ana Vanessa Camila Nogueira", "28013225984");
    AssociadoRequest request = new AssociadoRequest(associado.getNome(), associado.getCpf());

    @Test
    void deveSalvarAssociadoComSucesso() {
        Associado associadoMapeado = new Associado(null, associado.getNome(), associado.getCpf());
        when(associadoRepository.existsByCpf(request.cpf())).thenReturn(false);
        when(associadoMapper.requestToModel(request)).thenReturn(associadoMapeado);
        when(associadoRepository.save(associadoMapeado)).thenReturn(associado);
        when(associadoMapper.modelToResponse(associado))
                .thenReturn(new AssociadoResponse(associado.getId(), associado.getNome(), associado.getCpf()));

        AssociadoResponse associadoSalvo = associadoService.salvar(request);

        assertEquals(1L, associadoSalvo.id());
        assertEquals("28013225984", associadoSalvo.cpf());
        verify(cpfService, times(1)).validacao(request.cpf());
    }

    @Test
    void deveLancarExcecaoParaCpfJaCadastrado() {
        when(associadoRepository.existsByCpf(request.cpf())).thenReturn(true);

        DadoDuplicadoException exception = assertThrows(
                DadoDuplicadoException.class, () -> associadoService.salvar(request)
        );

        assertEquals("CPF informado já cadastrado.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        when(associadoRepository.existsByCpf(request.cpf())).thenReturn(false);
        doThrow(new NotFoundException("CPF inválido")).when(cpfService).validacao(request.cpf());

        NotFoundException exception = assertThrows(
                NotFoundException.class, () -> associadoService.salvar(request)
        );

        assertEquals("CPF inválido", exception.getMessage());
        verify(cpfService, times(1)).validacao(request.cpf());
    }
}
