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
        Integer valorPrincipal = 100;
        Integer valorTaxas = 10;
        String descricao = "descricao_teste";
        String url = "https://urlteste";
        Instant expiration = Instant.now();
        EnumStatusLinkPagamento status = EnumStatusLinkPagamento.WAITING_PAIMENT;

        Map<String, Object> jsonAsMap = Map.of(
                "id", id,
                "id_cobranca", idCobranca,
                "valor_principal", valorPrincipal,
                "valor_taxas", valorTaxas,
                "descricao", descricao,
                "url", url,
                "expiration", expiration,
                "status", status);
        LinkPagamentoModel2 modelFromJson = JsonWrapper.toObject(jsonAsMap, LinkPagamentoModel2.class);

        LinkPagamentoModel2 modelFromNew = new LinkPagamentoModel2(id,
                idCobranca,
                valorPrincipal,
                valorTaxas,
                descricao,
                url,
                expiration,
                status);

        assertEquals(modelFromNew.getId(), modelFromJson.getId());
        assertEquals(modelFromNew.getIdCobranca(), modelFromJson.getIdCobranca());
        assertEquals(modelFromNew.getValorPrincipal(), modelFromJson.getValorPrincipal());
        assertEquals(modelFromNew.getValorTaxas(), modelFromJson.getValorTaxas());
        assertEquals(modelFromNew.getDescricao(), modelFromJson.getDescricao());
        assertEquals(modelFromNew.getUrl(), modelFromJson.getUrl());
        assertEquals(modelFromNew.getExpiration(), modelFromJson.getExpiration());
        assertEquals(modelFromNew.getStatus(), modelFromJson.getStatus());

        assertEquals(modelFromNew, modelFromJson);
    }

}