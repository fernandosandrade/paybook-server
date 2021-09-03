package org.paybook.com.services.link_pagamento.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.JsonWrapper;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class LinkPagamentoModelTest {

    @Test
    void popularFromMapTest() {
        String id = "id_teste";
        String idCobranca = "id_cobranca_teste";
        Integer valorPrincipal = 1000;
        Integer valorTaxas = 1000;
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

        LinkPagamentoModel modelFromNew = new LinkPagamentoModel.Builder()
                .documentID(id)
                .idCobranca(idCobranca)
                .valorPrincipal(valorPrincipal)
                .valorTaxas(valorTaxas)
                .descricao(descricao)
                .url(url)
                .vencimento(vencimento)
                .status(status)
                .build();

        assertEquals(modelFromNew.documentID(), modelFromJson.documentID());
        assertEquals(modelFromNew.idCobranca(), modelFromJson.idCobranca());
        assertEquals(modelFromNew.valorPrincipal(), modelFromJson.valorPrincipal());
        assertEquals(modelFromNew.valorTaxas(), modelFromJson.valorTaxas());
        assertEquals(modelFromNew.descricao(), modelFromJson.descricao());
        assertEquals(modelFromNew.url(), modelFromJson.url());
        assertEquals(modelFromNew.vencimento(), modelFromJson.vencimento());
        assertEquals(modelFromNew.status(), modelFromJson.status());

        assertEquals(modelFromNew, modelFromJson);
    }

}