package com.assembleia.app.votacao.service;

import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.enums.SessaoStatus;
import com.assembleia.app.votacao.enums.Voto;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.SessaoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Sessao;
import com.assembleia.app.votacao.model.VotoSessao;
import com.assembleia.app.votacao.repository.SessaoRepository;
import com.assembleia.app.votacao.service.impl.SessaoServiceImpl;
import com.assembleia.app.votacao.stubs.AssociadoStub;
import com.assembleia.app.votacao.stubs.SessaoStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceImplTest {
    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private AssociadoService associadoService;
    @Mock
    private SessaoMapper sessaoMapper;
    @InjectMocks
    private SessaoServiceImpl sessaoService;

    Sessao sessaoStub = SessaoStub.criarSessao(
            LocalDateTime.of(2023, 1, 1, 12, 0),
            LocalDateTime.of(2023, 1, 1, 13, 0));

    Associado associadoStub = AssociadoStub.criarAssociado(1L, "João L.", "01268572020");

    @Test
    void deveRetornarSessaoComPautaAprovada() {
        sessaoStub.setVotos(List.of(new VotoSessao(Voto.SIM, new Associado())));

        when(sessaoRepository.findById(1L))
                .thenReturn(Optional.of(sessaoStub));

        when(sessaoMapper.modelToSimpleResponse(sessaoStub))
                .thenReturn(SessaoStub.criarSessaoResponse(sessaoStub));

        SessaoResponse sessaoResponse = sessaoService.buscarPorId(1L);

        assertEquals(SessaoStatus.PAUTA_APROVADA, sessaoResponse.status());
        assertEquals(1L, sessaoResponse.votosSim());
        assertEquals(0L, sessaoResponse.votosNao());
    }

    @Test
    void deveRetornarSessaoComPautaReprovada() {
        sessaoStub.setVotos(List.of(new VotoSessao(Voto.NAO, new Associado())));

        when(sessaoRepository.findById(1L))
                .thenReturn(Optional.of(sessaoStub));

        when(sessaoMapper.modelToSimpleResponse(sessaoStub))
                .thenReturn(SessaoStub.criarSessaoResponse(sessaoStub));

        SessaoResponse sessaoResponse = sessaoService.buscarPorId(1L);

        assertEquals(SessaoStatus.PAUTA_REPROVADA, sessaoResponse.status());
        assertEquals(0L, sessaoResponse.votosSim());
        assertEquals(1L, sessaoResponse.votosNao());
    }

    @Test
    void deveLancarExcecaoParaEventoEncerradoAoTentarReceberVoto() {
        sessaoStub.setVotos(List.of(new VotoSessao(Voto.NAO, new Associado())));

        when(sessaoRepository.findById(1L))
                .thenReturn(Optional.of(sessaoStub));

        when(associadoService.buscarPorCpf(associadoStub.getCpf()))
                .thenReturn(associadoStub);

        VotoRequest request = new VotoRequest(associadoStub.getCpf(), Voto.SIM);
        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class, () -> sessaoService.receberVoto(sessaoStub.getId(), request)
        );

        assertEquals("Sessão não está em andamento.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoTentarReceberVotoDuplicado() {
        sessaoStub.setDataInicio(LocalDateTime.now());
        sessaoStub.setDataFim(LocalDateTime.now().plusHours(1));

        when(sessaoRepository.findById(1L))
                .thenReturn(Optional.of(sessaoStub));

        when(associadoService.buscarPorCpf(associadoStub.getCpf()))
                .thenReturn(associadoStub);

        when(sessaoRepository.existsByVotosIdSessaoIdAndVotosIdAssociadoId(sessaoStub.getId(), associadoStub.getId()))
                .thenReturn(true);

        VotoRequest request = new VotoRequest(associadoStub.getCpf(), Voto.SIM);
        UnprocessableEntityException exception = assertThrows(
                UnprocessableEntityException.class, () -> sessaoService.receberVoto(sessaoStub.getId(), request)
        );

        assertEquals("O associado já registrou voto na sessão.", exception.getMessage());
    }
}
