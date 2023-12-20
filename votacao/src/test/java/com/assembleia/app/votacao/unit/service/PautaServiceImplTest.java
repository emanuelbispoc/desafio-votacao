package com.assembleia.app.votacao.unit.service;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.AssociadoMapper;
import com.assembleia.app.votacao.mapper.PautaMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.repository.PautaRepository;
import com.assembleia.app.votacao.service.impl.AssociadoServiceImpl;
import com.assembleia.app.votacao.service.impl.PautaServiceImpl;
import com.assembleia.app.votacao.stubs.AssociadoStub;
import com.assembleia.app.votacao.stubs.PautaStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceImplTest {
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private AssociadoServiceImpl associadoService;
    @Mock
    private AssociadoMapper associadoMapper;
    @Mock
    private PautaMapper pautaMapper;


    @InjectMocks
    private PautaServiceImpl pautaService;

    Associado associado = AssociadoStub.criarAssociado(1L, "Esther Maya Pinto", "13527751408");
    AssociadoResponse associadoResponse = AssociadoStub.criarAssociadoResponse(associado);
    Pauta pauta = PautaStub.criarPauta(1L, "Pauta teste 1", associado);

    @Test
    void deveSalvarPautaComSucesso() {
        PautaRequest pautaRequest = new PautaRequest("Pauta teste 1", 1L);
        when(associadoService.buscarPorId(pautaRequest.associadoId()))
                .thenReturn(associadoResponse);
        when(pautaMapper.requestToModel(pautaRequest)).thenReturn(pauta);
        when(associadoMapper.responseToModel(associadoResponse)).thenReturn(associado);
        when(pautaRepository.save(pauta)).thenReturn(pauta);
        when(pautaMapper.modelToResponse(pauta))
                .thenReturn(PautaStub.criarPautaResponse(pauta));


        PautaResponse pautaSalva = pautaService.salvar(pautaRequest);
        assertEquals(1L, pautaSalva.id());
        assertEquals("Esther Maya Pinto", pautaSalva.relatorNome());
    }

    @Test
    void deveLancarExcecaoAoTentarCadastrarPautaComAssociadoInexistente() {
        PautaRequest pautaRequest = new PautaRequest("Pauta teste 1", 1L);
        when(associadoService.buscarPorId(pautaRequest.associadoId()))
                .thenThrow(new NotFoundException("Associado não encontrado."));

        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class, () -> pautaService.salvar(pautaRequest)
        );

        assertEquals("Associado não encontrado.", exception.getMessage());
        verify(associadoService, times(1)).buscarPorId(pautaRequest.associadoId());
    }
}
