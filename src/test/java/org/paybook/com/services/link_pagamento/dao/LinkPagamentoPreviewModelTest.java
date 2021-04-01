package org.paybook.com.services.link_pagamento.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.JsonWrapper;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class LinkPagamentoPreviewModelTest {

    @Test
    void testFromLinkPagamentoModel() {
        String id = "id_teste";
        String idCobranca = "id_cobranca_teste";
        Integer valorPrincipal = 100;
        Integer valorTaxas = 10;
        String descricao = "descricao_teste";
        String url = "https://urlteste";
        Instant vencimento = Instant.now();
        EnumStatusLinkPagamento status = EnumStatusLinkPagamento.WAITING_PAIMENT;

        Map<String, Object> jsonAsMap = Map.of(
                "id", id,
                "id_cobranca", idCobranca,
                "valor_principal", valorPrincipal,
                "valor_taxas", valorTaxas,
                "descricao", descricao,
                "url", url,
                "vencimento", vencimento,
                "status", status);
        LinkPagamentoModel modelFromJson = JsonWrapper.toObject(jsonAsMap, LinkPagamentoModel.class);


        LinkPagamentoPreviewModel linkPagamentoPreviewModel = LinkPagamentoPreviewModel.from(modelFromJson);

        assertEquals(id, linkPagamentoPreviewModel.id());
        assertEquals(valorPrincipal, linkPagamentoPreviewModel.valor());
        assertEquals(vencimento, linkPagamentoPreviewModel.vencimento());
        assertEquals(status, linkPagamentoPreviewModel.status());
    }

}