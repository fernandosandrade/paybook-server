package org.paybook.com.services.link_pagamento.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.DefaultTimes;
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

        assertEquals(id, linkPagamentoPreviewModel.getId());
        assertEquals(valorPrincipal, linkPagamentoPreviewModel.getValor());
        assertEquals(vencimento, linkPagamentoPreviewModel.getVencimento());
        assertEquals(status, linkPagamentoPreviewModel.getStatus());
    }

    @Test
    void testLinkPagamentoPreviewModelDesserialization() {
        String id = "id_teste";
        Integer valor = 10000;
        Instant vencimento = Instant.now();
        EnumStatusLinkPagamento status = EnumStatusLinkPagamento.WAITING_PAIMENT;

        LinkPagamentoPreviewModel previewModel = LinkPagamentoPreviewModel.builder()
                .id(id)
                .valor(valor)
                .vencimento(vencimento)
                .status(status)
                .build();

        Map<String, Object> jsonAsMap = Map.of(
                "id", id,
                "valor", valor,
                "vencimento", vencimento,
                "status", status);

        LinkPagamentoPreviewModel modelFromJson = JsonWrapper.toObject(jsonAsMap, LinkPagamentoPreviewModel.class);

        assertEquals(id, modelFromJson.getId());
        assertEquals(valor, modelFromJson.getValor());
        assertEquals(vencimento, modelFromJson.getVencimento());
        assertEquals(status, modelFromJson.getStatus());

        assertEquals(previewModel, modelFromJson);
    }

    @Test
    void testLinkPagamentoPreviewModelSerialization() {
        String id = "id_teste";
        Integer valor = 10000;
        Instant vencimento = Instant.now();
        EnumStatusLinkPagamento status = EnumStatusLinkPagamento.WAITING_PAIMENT;

        LinkPagamentoPreviewModel previewModel = LinkPagamentoPreviewModel.builder()
                .id(id)
                .valor(valor)
                .vencimento(vencimento)
                .status(status)
                .build();

        Map<String, Object> mapFromObject = JsonWrapper.fromObject(previewModel);

        assertEquals(id, mapFromObject.get("id"));
        assertEquals(valor, mapFromObject.get("valor"));
        assertEquals(vencimento.toEpochMilli(), mapFromObject.get("vencimento"));
        assertEquals(status.name(), mapFromObject.get("status"));
    }

    @Test
    void assertObjectEquals() {
        String id = "id_teste";
        Integer valor = 10000;
        Instant vencimento = DefaultTimes.now();
        EnumStatusLinkPagamento status = EnumStatusLinkPagamento.WAITING_PAIMENT;

        LinkPagamentoPreviewModel previewModel = LinkPagamentoPreviewModel.builder()
                .id(id)
                .valor(valor)
                .vencimento(vencimento)
                .status(status)
                .build();

        Map<String, Object> mapFromObject = JsonWrapper.fromObject(previewModel);
        LinkPagamentoPreviewModel modelResult = JsonWrapper.toObject(mapFromObject,
                LinkPagamentoPreviewModel.class);

        assertEquals(previewModel, modelResult);
    }


}