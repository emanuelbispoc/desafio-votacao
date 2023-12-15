package com.assembleia.app.votacao.integration.service;

import com.assembleia.app.votacao.dto.response.CpfValidacaoResponse;
import com.assembleia.app.votacao.enums.SituacaoCpf;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.service.impl.CpfServiceImpl;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(httpPort = 8089)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CpfServiceImplTest {
    @Autowired
    private CpfServiceImpl cpfService;

    @Test
    void deveRealizarValidacaoComSucesso() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/72694294057"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")

                )
        );

        assertDoesNotThrow(()-> cpfService.validacao("72694294057"));
    }

    @Test
    void deveLancarNotFoundParaCpfInvalido() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/52541628056"))
                .willReturn(aResponse()
                                .withStatus(404)
                                .withHeader("Content-Type", "application/json")

                )
        );

        NotFoundException exception = assertThrows(
                NotFoundException.class, () -> cpfService.validacao("52541628056")
        );

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void deveRetornarSituacaoIrregularCorretamente() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/97076518066/situacao"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"IRREGULAR\"}\n")

                )
        );

        CpfValidacaoResponse cpfValidacaoResponse = cpfService.verificaSituacao("97076518066");
        assertEquals(SituacaoCpf.IRREGULAR, cpfValidacaoResponse.status());
    }

    @Test
    void deveRetornarSituacaoRegularCorretamente() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/35460280079/situacao"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"IRREGULAR\"}\n")

                )
        );

        CpfValidacaoResponse cpfValidacaoResponse = cpfService.verificaSituacao("35460280079");
        assertEquals(SituacaoCpf.IRREGULAR, cpfValidacaoResponse.status());
    }
}
