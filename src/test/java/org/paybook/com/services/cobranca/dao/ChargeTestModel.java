package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * Cobranca para testes do {@link ChargeBaseModel}
 */
@JsonDeserialize(builder = ChargeTestModel.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface ChargeTestModel extends ChargeBaseModel {

    @JsonProperty("atributo_teste")
    String atributoTeste();

    class Builder extends ImmutableChargeTestModel.Builder {
    }

}
