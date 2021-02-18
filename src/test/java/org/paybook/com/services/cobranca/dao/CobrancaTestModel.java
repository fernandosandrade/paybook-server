package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = ImmutableCobrancaTestModel.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface CobrancaTestModel extends CobrancaBaseModel {

    @JsonProperty("atributo_teste")
    String atributoTeste();

    class Builder extends ImmutableCobrancaTestModel.Builder {
    }

}
