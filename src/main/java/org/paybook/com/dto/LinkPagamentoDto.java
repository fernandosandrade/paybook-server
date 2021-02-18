package org.paybook.com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkPagamentoDto {

    /** id do link de pagamento */
    private String id;

    /** id da cobranca a qual o link ficara vinculado */
    @JsonProperty("id_cobranca")
    private String idCobranca;

    /** valor princilap da cobranca, com os decimais *100. Ex: 123,45 => 12345 */
    @JsonProperty("valor_principal")
    private Integer valorPrincipal;

    /** descricao que pode acompanhar o link gerado */
    private String descricao;

    /** data em que esta cobranca passa a nao ser mais valida */
    @JsonProperty("data_expiracao")
    private Instant dataExpiracao;

    /** status do link de pagamento */
    private EnumStatusLinkPagamento status;
}
