package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.paybook.com.GoogleTimestampAdapter;

import java.time.Instant;

@JsonDeserialize(builder = Charge111Model.Builder.class)
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, overshadowImplementation = true)
@Value.Immutable
public interface Charge111Model extends ChargeBaseModel {

    @JsonProperty("expiration_date")
    @JsonDeserialize(using = GoogleTimestampAdapter.Deserializer.class)
    Instant expirationDate();

    class Builder extends ImmutableCharge111Model.Builder {
    }
}

