package org.paybook.com.services.cobranca;

import lombok.Value;
import org.paybook.com.services.Destinatario;
import org.paybook.com.utils.RandomString;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Value
public class CobrancaDocumentExample {

    private Map<String, Object> data;

    public static CobrancaDocumentExample newTestInstance() {
        return new CobrancaDocumentExample(Map.of(
                "id_cobranca", RandomString.next(),
                "id_book", "teste",
                "destinatario",
                new Destinatario("sr.strass@gmail.com",
                        "fernando",
                        "51999467985"),
                "valor", 1000,
                "data_vencimento", Instant.now().plus(30, ChronoUnit.DAYS).toEpochMilli(),
                "data_criacao", Instant.now().toEpochMilli(),
                "status", EnumStatusCobranca.CHARGE_OPEN.name(),
                "atributo_teste", "valor atributo teste",
                "links_cobranca", List.of("link_01", "link_02"))
        );
    }

}
