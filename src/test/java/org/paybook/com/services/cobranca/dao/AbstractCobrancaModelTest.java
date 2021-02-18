package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.JsonWrapper;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.CobrancaDocumentExample;
import org.paybook.com.services.cobranca.EnumStatusCobranca;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AbstractCobrancaModelTest {

    @Test
    void testCobrancaModelFromMap() {
        final var data = CobrancaDocumentExample.newTestInstance().getData();

        CobrancaTestModel cobrancaModel = JsonWrapper.toObject(data, CobrancaTestModel.class);

        assertEquals(data.get("id_cobranca"), cobrancaModel.idCobranca());
        assertEquals(data.get("id_book"), cobrancaModel.idBook());
        assertEquals(data.get("destinatario"), cobrancaModel.destinatario());
        assertEquals(data.get("valor"), cobrancaModel.valor());
        assertEquals(data.get("data_vencimento"), cobrancaModel.dataVencimento().toEpochMilli());
        assertEquals(data.get("data_criacao"), cobrancaModel.dataCriacao().toEpochMilli());
        assertEquals(EnumStatusCobranca.valueOf(data.get("status").toString()), cobrancaModel.status());
        assertEquals(data.get("atributo_teste"), cobrancaModel.atributoTeste());
        assertEquals(data.get("links_cobranca"), cobrancaModel.linksCobranca());
    }

    @Test
    void testModelserialize() throws JsonProcessingException {
        final CobrancaTestModel cobrancaModel = new CobrancaTestModel.Builder()
                .idCobranca("teste")
                .idBook("teste")
                .dataCriacao(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .dataVencimento(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .valor(10000)
                .destinatario(new Destinatario("teste@teste.com.br", "fernando", "51999467985"))
                .addLinksCobranca("link_01", "link_02")
                .status(EnumStatusCobranca.CHARGE_OPEN)
                .atributoTeste("teste")
                .build();

        final var jsonMap = JsonWrapper.fromObject(cobrancaModel);
        assertEquals(cobrancaModel.idCobranca(), jsonMap.get("id_cobranca"));
        assertEquals(cobrancaModel.idBook(), jsonMap.get("id_book"));
        assertEquals(cobrancaModel.dataCriacao(),
                Instant.ofEpochMilli(Long.valueOf(jsonMap.get("data_criacao").toString())));
        assertEquals(cobrancaModel.dataVencimento(),
                Instant.ofEpochMilli(Long.valueOf(jsonMap.get("data_vencimento").toString())));
        assertEquals(cobrancaModel.valor(), jsonMap.get("valor"));
        assertEquals(cobrancaModel.destinatario().getNome(), ((Map) jsonMap.get("destinatario")).get("nome"));
        assertEquals(cobrancaModel.destinatario().getEmail(), ((Map) jsonMap.get("destinatario")).get("email"));
        assertEquals(cobrancaModel.destinatario().getTelefone(), ((Map) jsonMap.get("destinatario")).get("telefone"));
        assertEquals(cobrancaModel.status(), EnumStatusCobranca.valueOf(jsonMap.get("status").toString()));
        assertEquals(cobrancaModel.atributoTeste(), jsonMap.get("atributo_teste"));
        assertEquals(cobrancaModel.linksCobranca(), jsonMap.get("links_cobranca"));
    }


}