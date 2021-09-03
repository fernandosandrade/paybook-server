package org.paybook.com;

import com.google.cloud.Timestamp;
import org.junit.jupiter.api.Test;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumChargeStatus;
import org.paybook.com.services.cobranca.dao.ChargeTestModel;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonWrapperTest {

    private static final String ID_COBRANCA = RandomString.next();

    @Test
    void fromObjectTest() {
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

        Map<String, Object> jsonMap = JsonWrapper.fromObject(cobrancaModel);
        Object creationDate = jsonMap.get("creation_date");
        Object vencimento = ((Map) ((List) jsonMap.get("payment_links")).get(0)).get("vencimento");
        assertTrue(creationDate instanceof Timestamp);
        assertTrue(vencimento instanceof Timestamp);
    }

    @Test
    void toObject() {
    }

    @Test
    void testFromObject() {
    }
}