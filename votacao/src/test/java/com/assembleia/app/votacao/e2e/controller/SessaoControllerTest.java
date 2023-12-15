package com.assembleia.app.votacao.e2e.controller;

import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.enums.SessaoStatus;
import com.assembleia.app.votacao.enums.Voto;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.assembleia.app.votacao.SqlProvider.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SqlGroup({
        @Sql(scripts = INSERT_ASSOCIADOS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = INSERT_SESSOES, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = CLEAR_DATABASE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@WireMockTest(httpPort = 8089)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessaoControllerTest {

    @LocalServerPort
    private int port;
    private static final String BASE_URL =  "/v1/sessoes";

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    @Test
    void deveReceberVotoComSucesso() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/51682883086/situacao"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"REGULAR\"}\n")

                )
        );

        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .body(new VotoRequest("51682883086", Voto.NAO))
                .post(BASE_URL + "/1/registra-voto")
        .then()
                .assertThat()
                .statusCode(200)
                .body(
                        "sessaoId", is(1),
                        "totalVotosSim", is(0),
                        "totalVotosNao", is(2)
                );
    }

    @Test
    void deveRetornarSessaoPorIdComStatusAprovado() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .get(BASE_URL + "/2")
        .then()
                .assertThat()
                .statusCode(200)
                .body(
                        "id", is(2),
                        "votosSim", is(2),
                        "votosNao", is(1),
                        "status",  is(SessaoStatus.PAUTA_APROVADA.toString()),
                        "dataInicio", is("2023-12-12T10:00:00"),
                        "dataFim", is("2023-12-12T15:00:00")
                );
    }

    @Test
    void deveRetornarSessaoPorIdComStatusReprovado() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .get(BASE_URL + "/3")
        .then()
                .assertThat()
                .statusCode(200)
                .body(
                        "id", is(3),
                        "votosSim", is(1),
                        "votosNao", is(2),
                        "status",  is(SessaoStatus.PAUTA_REPROVADA.toString()),
                        "dataInicio", is("2023-12-12T14:00:00"),
                        "dataFim", is("2023-12-13T09:00:00")
                );
    }

    @Test
    void deveRetornarErroParaAssociadoQueJaVotou() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .body(new VotoRequest("85903326323", Voto.SIM))
                .post(BASE_URL + "/1/registra-voto")
        .then()
                .assertThat()
                .statusCode(422)
                .body(
                        "descricao", is("O associado já registrou voto na sessão.")
                );
    }

    @Test
    void deveRetornarErroAoTentarReceberVotoAposEncerramento() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .body(new VotoRequest("51682883086", Voto.SIM))
                .post(BASE_URL + "/2/registra-voto")
        .then()
                .assertThat()
                .statusCode(422)
                .body(
                        "descricao", is("Sessão não está em andamento.")
                );
    }

    @Test
    void deveRetornarErroAoTentarReceberVotoAssociadoNaoRegistrado() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .body(new VotoRequest("20425656870", Voto.SIM))
                .post(BASE_URL + "/1/registra-voto")
        .then()
                .assertThat()
                .statusCode(422)
                .body(
                        "descricao", is("Associado não encontrado.")
                );
    }

    @Test
    void deveRetornarErroAoTentarReceberVotoAssociadoCpfIrregular() {
        WireMock.stubFor(WireMock.get(urlEqualTo("/81342636074/situacao"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"IRREGULAR\"}\n")

                )
        );

        given()
                .contentType(ContentType.JSON)
                .port(port)
        .when()
                .body(new VotoRequest("81342636074", Voto.SIM))
                .post(BASE_URL + "/1/registra-voto")
        .then()
                .assertThat()
                .statusCode(422)
                .body(
                        "descricao", is("UNABLE_TO_VOTE")
                );
    }

}