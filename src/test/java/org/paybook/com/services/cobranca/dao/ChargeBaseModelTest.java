package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.Timestamp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.paybook.com.DefaultTimes;
import org.paybook.com.EnumChargeType;
import org.paybook.com.JsonWrapper;
import org.paybook.com.RandomString;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.DocumentReferencePath;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumChargeStatus;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChargeBaseModelTest {

    private static final String ID_COBRANCA = RandomString.next();

    private static final String ID_BOOK = "abcd";

    private static final Destinatario DESTINATARIO =
            new Destinatario("sr.strass@gmail.com",
                    "fernando",
                    "51999467985");

    private static final Instant DATA_CRIACAO = DefaultTimes.now();

    private static final Instant DATA_VENCIMENTO = DefaultTimes.now().plus(1, ChronoUnit.DAYS);

    private static final String VALOR_ATRIBUTO_TESTE = "valor atributo teste";

    private static List<LinkPagamentoPreviewModel> linksPagamentoPreview;

    private static DBDocument document;

    @BeforeAll
    static void init() {
        document = DBDocument.from(Map.of(
                        "document_id", ID_COBRANCA,
                        "id_book", ID_BOOK,
                        "destinatario", DESTINATARIO,
                        "valor", 1000,
                        "data_vencimento", DATA_VENCIMENTO,
                        "data_criacao", DATA_CRIACAO,
                        "status", EnumChargeStatus.CHARGE_OPEN.name(),
                        "atributo_teste", VALOR_ATRIBUTO_TESTE,
                        "links_pagamento", List.of()),
                DocumentReferencePath.of("test_path").getPath(),
                ID_COBRANCA);
    }

    @Test
    void testCobrancaModelFromMap() {
        ChargeTestModel cobrancaModel = JsonWrapper.toObject(document.data(), ChargeTestModel.class);

        assertEquals(document.data().get("document_id"), cobrancaModel.documentID());
        assertEquals(document.data().get("destinatario"), cobrancaModel.receiver());
        assertEquals(document.data().get("valor"), cobrancaModel.amount());
        assertEquals(document.data().get("data_criacao"), cobrancaModel.creationDate());
        assertEquals(EnumChargeStatus.valueOf(document.data().get("status").toString()), cobrancaModel.status());
        assertEquals(document.data().get("atributo_teste"), cobrancaModel.atributoTeste());

    }

    @Test
    void testModelSerialize() throws JsonProcessingException {
        final ChargeTestModel cobrancaModel = new ChargeTestModel.Builder()
                .documentID(ID_COBRANCA)
                .chargeType(EnumChargeType.C_111)
                .creationDate(DATA_CRIACAO)
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

        final var jsonMap = JsonWrapper.fromObject(cobrancaModel);
        assertEquals(cobrancaModel.documentID(), jsonMap.get("document_id"));
        assertEquals(cobrancaModel.creationDate(),
                Instant.ofEpochMilli(Long.valueOf(jsonMap.get("creation_date").toString())));
        assertEquals(cobrancaModel.amount(), jsonMap.get("value"));
        assertEquals(cobrancaModel.receiver().getNome(), ((Map) jsonMap.get("receiver")).get("nome"));
        assertEquals(cobrancaModel.receiver().getEmail(), ((Map) jsonMap.get("receiver")).get("email"));
        assertEquals(cobrancaModel.receiver().getTelefone(), ((Map) jsonMap.get("receiver")).get("telefone"));
        assertEquals(cobrancaModel.status(), EnumChargeStatus.valueOf(jsonMap.get("status").toString()));
        assertEquals(cobrancaModel.atributoTeste(), jsonMap.get("atributo_teste"));
        LinkPagamentoPreviewModel linkPagamentoPreviewModel = cobrancaModel.paymentLinks().get(0);
        assertEquals(cobrancaModel.paymentLinks().get(0),
                JsonWrapper.toObject((Map<String, Object>) ((ArrayList) jsonMap.get("links_pagamento")).get(0),
                        LinkPagamentoPreviewModel.class));
    }

    private void parseDates(Map objectMap) {
        objectMap.forEach((k, v) -> {
            if (v instanceof String) {
                try {
                    Timestamp timestamp = Timestamp.parseTimestamp((String) v);
                    objectMap.replace(k, timestamp);
                } catch (Exception e) {
                }
            } else if (v instanceof List) {
                ((List<Map>) v).forEach(this::parseDates);
            }
        });
    }

    @Test
    void teste2() throws JsonProcessingException {
        String json = "{\n" +
                "  \"charge_type\": 111,\n" +
                "  \"payment_links\": [],\n" +
                "  \"receiver\": {\n" +
                "    \"telefone\": \"998875997\",\n" +
                "    \"nome\": \"Amogueco\",\n" +
                "    \"email\": \"aline@gmail.com\"\n" +
                "  },\n" +
                "  \"creation_date\": {\n" +
                "    \"seconds\": 1628262000,\n" +
                "    \"nanos\": 999331\n" +
                "  },\n" +
                "  \"document_id\": \"5X00Y2G3KCIY9D2VP4H9\",\n" +
                "  \"value\": 123,\n" +
                "  \"status\": 0\n" +
                "}";

        Map map = JsonWrapper.getDefaultMapper().readValue(json, Map.class);
        System.out.println(map);
    }

}