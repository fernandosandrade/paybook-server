package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = Cobranca112Model.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface Cobranca112Model extends CobrancaBaseModel {

    class Builder extends ImmutableCobranca112Model.Builder {
    }

}
