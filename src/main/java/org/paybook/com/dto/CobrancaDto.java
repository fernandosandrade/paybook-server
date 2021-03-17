package org.paybook.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumStatusCobranca;

import java.time.Instant;
import java.util.List;

@Getter
@SuperBuilder
public class CobrancaDto implements DtoObject {

    @JsonProperty("id_cobranca")
    private String idCobranca;

    @JsonProperty("id_book")
    private String idBook;

    private Destinatario destinatario;

    private Integer valor;

    @JsonProperty("data_vencimento")
    private Instant dataVencimento;

    @JsonProperty("data_criacao")
    private Instant dataCriacao;

    EnumStatusCobranca status;

    @JsonProperty("links_cobranca")
    private List<String> linksCobranca;
}
