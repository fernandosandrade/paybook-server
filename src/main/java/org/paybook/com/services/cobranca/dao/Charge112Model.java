package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(builder = Charge112Model.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface Charge112Model extends ChargeBaseModel {

    class Builder extends ImmutableCharge112Model.Builder {
    }

}
