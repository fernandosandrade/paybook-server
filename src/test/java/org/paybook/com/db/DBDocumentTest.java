package org.paybook.com.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.EnumChargeType;
import org.paybook.com.RandomString;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumChargeStatus;
import org.paybook.com.services.cobranca.dao.ChargeTestModel;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class DBDocumentTest {

    private static final String ID_COBRANCA = RandomString.next();

    @Test
    void getIDandCollectionPathTest() {
        DBDocument dbDocument = DBDocument.from(Map.of(),
                DocumentReferencePath.of("cobranca", "101", "111").getPath(),
                "123456");

        assertEquals("cobranca/101/111/123456", dbDocument.documentReference());
        assertEquals("cobranca/101/111", dbDocument.collection());
        assertEquals("123456", dbDocument.id());
    }

    @Test
    void removeIdFromObjectTest() {
        final ChargeTestModel cobrancaModel = new ChargeTestModel.Builder()
                .documentID(ID_COBRANCA)
                .chargeType(EnumChargeType.C_111)
                .creationDate(Instant.now())
                .amount(10000)
                .receiver(new Destinatario("teste@teste.com.br", "fernando", "51999467985"))
                .addPaymentLinks(LinkPagamentoPreviewModel.builder()
                        .id("id_link_pagamento")
                        .valor(1000)
                        .vencimento(Instant.now())
                        .status(EnumStatusLinkPagamento.WAITING_PAIMENT)
                        .build())
                .status(EnumChargeStatus.CHARGE_OPEN)
                .atributoTeste("teste")
                .build();
    }

}