package org.paybook.com.services.link_pagamento.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;

@JBossLog
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class LinkPagamentoModel2 {

    /** id do link de pagamento */
    private String id;

    /** id estrangeiro, que permite associar este link a um componente externo, como uma cobrança */
    @JsonProperty("id_cobranca")
    private String idCobranca;

    /** valor da cobranca, com os decimais *100. Ex: 123,45 => 12345 */
    @JsonProperty("valor_principal")
    private Integer valorPrincipal;

    /** valor da cobranca, com os decimais *100. Ex: 123,45 => 12345 */
    @JsonProperty("valor_taxas")
    private Integer valorTaxas;

    /** descricao que pode acompanhar o link gerado */
    private String descricao;

    /** url gerada para a cobranca */
    private String url;

    /** data em que esta cobranca passa a nao ser mais valida */
    private Instant expiration;

    /** status do link de pagamento */
    private EnumStatusLinkPagamento status;

}
