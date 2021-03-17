package org.paybook.com.controller.v1;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.paybook.com.dto.Cobranca111Dto;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.Cobranca111Service;
import org.paybook.com.services.cobranca.EnumStatusCobranca;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static io.restassured.RestAssured.given;

@QuarkusTest
class Cobranca111Test {

    @InjectMock
    Cobranca111Service cobrancaService;

    @Test
    public void obterTeste() {
        Cobranca111Model cobranca111Model = new Cobranca111Model.Builder()
                .idCobranca("teste")
                .idBook("id_book")
                .destinatario(new Destinatario("sr.strass@gmail.com", "fernando", "999467985"))
                .dataCriacao(Instant.now())
                .dataVencimento(Instant.now().plus(10, ChronoUnit.DAYS))
                .status(EnumStatusCobranca.WAITING_PAYMENT)
                .valor(100)
                .build();

        Mockito.when(this.cobrancaService.obter("teste")).thenReturn(Optional.of(cobranca111Model));
        Response response = given()
                .when().get("/v1/cobranca/101/111/teste");

        Assertions.assertEquals(response.statusCode(), 200);

        Cobranca111Model as = response.getBody().as(Cobranca111Model.class);
        Assertions.assertEquals(as, cobranca111Model);
    }

    @Test
    public void alterarStatusTeste() {
        Cobranca111Dto cobranca = Cobranca111Dto.builder().status(EnumStatusCobranca.CHARGE_CANCELED).build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(cobranca)
                .when()
                .patch("/v1/cobranca/101/111/1");
        Cobranca111Dto responseDto = response.getBody().as(Cobranca111Dto.class);
        Assertions.assertEquals(response.statusCode(), 200);
    }

}
