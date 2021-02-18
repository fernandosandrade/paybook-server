package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = ImmutableCobranca111Model.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface Cobranca111Model extends CobrancaBaseModel {

    class Builder extends ImmutableCobranca111Model.Builder {}
}

