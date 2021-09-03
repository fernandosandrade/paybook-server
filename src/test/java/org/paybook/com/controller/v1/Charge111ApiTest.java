package org.paybook.com.controller.v1;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.paybook.com.controller.dto.ChargeDto;
import org.paybook.com.services.ChargesCollection;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.Charge111Service;
import org.paybook.com.services.cobranca.EnumChargeStatus;
import org.paybook.com.services.cobranca.dao.Charge111Model;
import org.paybook.com.services.link_pagamento.LinkPagamentoFactory;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@QuarkusTest
class Charge111ApiTest {

    @InjectMock
    Charge111Service chargeService;

    static final String CHARGE_ID = "47520016904330182046";

    final Charge111Model charge111Model = new Charge111Model.Builder()
            .documentID(CHARGE_ID)
            .receiver(new Destinatario("sr.strass@gmail.com", "fernando", "999467985"))
            .creationDate(Instant.now())
            .expirationDate(Instant.now().plus(10, ChronoUnit.DAYS))
            .status(EnumChargeStatus.CHARGE_OPEN)
            .amount(100)
            .build();

    @Test
    public void createPaymentLink_test() {
//        /users/9lSbTlfCh3UawuxgGS63F9zNAJw2/books/FMF92AK0AA7WNMLFV5Ol/charges/47520016904330182046
        ChargeDto chargeDto = ChargeDto.builder().user("9lSbTlfCh3UawuxgGS63F9zNAJw2")
                .book("FMF92AK0AA7WNMLFV5Ol")
                .charge(CHARGE_ID)
                .build();

        ChargesCollection chargesCollection = ChargesCollection.of(chargeDto.getUser(),
                chargeDto.getBook());

        var linkPagamentoModel = LinkPagamentoFactory.from(this.charge111Model.amount(),
                this.charge111Model.expirationDate(),
                this.charge111Model.documentID(),
                "descricao para o link");

        Charge111Model model = new Charge111Model.Builder().from(this.charge111Model)
                .status(EnumChargeStatus.CHARGE_OPEN)
                .build();

        Mockito.when(this.chargeService.find(eq(CHARGE_ID), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(Optional.of(model)));

        Mockito.when(this.chargeService.generatePaymentLinks(any(Charge111Model.class), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(new Charge111Model.Builder().from(this.charge111Model)
                        .status(EnumChargeStatus.WAITING_PAYMENT)
                        .addPaymentLinks(
                                LinkPagamentoPreviewModel.from(
                                        linkPagamentoModel))
                        .build()));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(chargeDto)
                .when().post("/v1/charges/101/111/paylinks");

        response.then()
                .log().all(true);

        model = new Charge111Model.Builder().from(this.charge111Model)
                .addPaymentLinks(LinkPagamentoPreviewModel.from(linkPagamentoModel))
                .status(EnumChargeStatus.WAITING_PAYMENT)
                .build();

        Charge111Model responseModel = response.getBody().as(Charge111Model.class);
        Assertions.assertEquals(model, responseModel);
    }

    @Test
    public void createPaymentLinkTest_invalid_charge_state() {
        ChargeDto chargeDto = ChargeDto.builder().user("9lSbTlfCh3UawuxgGS63F9zNAJw2")
                .book("FMF92AK0AA7WNMLFV5Ol")
                .charge(CHARGE_ID)
                .build();

        Charge111Model model =
                new Charge111Model.Builder().from(this.charge111Model)
                        .status(EnumChargeStatus.CHARGE_PAID)
                        .build();

        Mockito.when(this.chargeService.find(eq(CHARGE_ID), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(Optional.of(model)));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(chargeDto)
                .when().post("/v1/charges/101/111/paylinks");

//        response.then()
//                .log().all(true);

        response
                .then()
                .statusCode(422)
                .body(is("expected status=CHARGE_OPEN, but receive CHARGE_PAID"));
    }

    @Test
    public void createPaymentLinkTest_link_already_setted() {
        ChargeDto chargeDto = ChargeDto.builder().user("9lSbTlfCh3UawuxgGS63F9zNAJw2")
                .book("FMF92AK0AA7WNMLFV5Ol")
                .charge(CHARGE_ID)
                .build();

        var linkPagamentoModel = LinkPagamentoFactory.from(this.charge111Model.amount(),
                this.charge111Model.expirationDate(),
                this.charge111Model.documentID(),
                "descricao para o link");

        Charge111Model model =
                new Charge111Model.Builder().from(this.charge111Model)
                        .addPaymentLinks(LinkPagamentoPreviewModel.from(linkPagamentoModel))
                        .build();

        Mockito.when(this.chargeService.find(eq(CHARGE_ID), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(Optional.of(model)));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(chargeDto)
                .when().post("/v1/charges/101/111/paylinks");

        response.then()
                .log().all(true);

        response
                .then()
                .statusCode(409)
                .body(is(MessageFormat.format("links for the charge id [{0}] has already be generated",
                        CHARGE_ID)));
    }

    @Test
    public void findTest_valid_id() {
        Mockito.when(this.chargeService.find(eq(CHARGE_ID), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(Optional.of(this.charge111Model)));

        Response response = given()
                .when().get("/v1/charges/101/111");

        Assertions.assertEquals(response.statusCode(), 200);

        Charge111Model responseCharge = response.getBody().as(Charge111Model.class);
        Assertions.assertEquals(responseCharge, this.charge111Model);
    }

    @Test
    public void findTest_invalid_id() {
        Mockito.when(this.chargeService.find(eq(CHARGE_ID), any(ChargesCollection.class)))
                .thenReturn(Uni.createFrom().item(Optional.empty()));

        Response response = given()
                .when().get("/v1/cobranca/101/111");

        Assertions.assertEquals(response.statusCode(), 404);

        Charge111Model responseCharge = response.getBody().as(Charge111Model.class);
        Assertions.assertEquals(responseCharge, this.charge111Model);
    }

}