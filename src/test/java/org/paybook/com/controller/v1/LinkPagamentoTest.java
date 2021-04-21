package org.paybook.com.controller.v1;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.paybook.com.controller.dto.LinkPagamentoDto;
import org.paybook.com.services.link_pagamento.LinkPagamentoService;

import static io.restassured.RestAssured.given;

@QuarkusTest
class LinkPagamentoTest {

    @InjectMock
    LinkPagamentoService linkPagamentoService;

    @Test
    public void criarTest() {
        LinkPagamentoDto linkPagamentoDto = LinkPagamentoDto.builder().idCobranca("idteste").build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(linkPagamentoDto)
                .when()
                .post("/v1/linkpagamento");
        LinkPagamentoDto responseDto = response.getBody().as(LinkPagamentoDto.class);
        Assertions.assertEquals(response.statusCode(), 200);
    }

}