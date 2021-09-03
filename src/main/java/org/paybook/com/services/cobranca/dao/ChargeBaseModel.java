package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.paybook.com.EnumChargeType;
import org.paybook.com.GoogleTimestampAdapter;
import org.paybook.com.db.DocumentRepositoryModel;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumChargeStatus;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.time.Instant;
import java.util.List;

public interface ChargeBaseModel extends DocumentRepositoryModel {
    @JsonProperty("charge_type")
    EnumChargeType chargeType();

    @JsonProperty("creation_date")
    @JsonDeserialize(using = GoogleTimestampAdapter.Deserializer.class)
    Instant creationDate();

    Integer amount();

    @JsonProperty("receiver")
    Destinatario receiver();

    EnumChargeStatus status();

    @JsonProperty("payment_links")
    List<LinkPagamentoPreviewModel> paymentLinks();

}
