package org.paybook.com.services.link_pagamento.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.paybook.com.db.DocumentRepositoryModel;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;

@JsonDeserialize(builder = LinkPagamentoModel.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface LinkPagamentoModel extends DocumentRepositoryModel {

//    /** id do link de pagamento */
//    String id();

    /** id estrangeiro, que permite associar este link a um componente externo, como uma cobrança */
    @JsonProperty("id_cobranca")
    String idCobranca();

    /** valor da cobranca, com os decimais *100. Ex: 123,45 => 12345 */
    @JsonProperty("valor_principal")
    Integer valorPrincipal();

    /** valor da cobranca, com os decimais *100. Ex: 123,45 => 12345 */
    @JsonProperty("valor_taxas")
    Integer valorTaxas();

    /** descricao que pode acompanhar o link gerado */
//    @Value.Default
//    default String descricao() {
//        return "not_informed";
//    }
    String descricao();

    /** url gerada para a cobranca */
    String url();

    /** data em que esta cobranca passa a nao ser mais valida */
    Instant vencimento();

    /** status do link de pagamento */
    EnumStatusLinkPagamento status();

    class Builder extends ImmutableLinkPagamentoModel.Builder {
    }


}
