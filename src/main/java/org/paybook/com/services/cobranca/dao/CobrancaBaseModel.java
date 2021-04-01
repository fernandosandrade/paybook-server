package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.paybook.com.db.DocumentRepositoryModel;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumStatusCobranca;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;

import java.time.Instant;
import java.util.List;

public interface CobrancaBaseModel extends DocumentRepositoryModel {

    @JsonProperty("id_cobranca")
    String idCobranca();

    @JsonProperty("id_book")
    String idBook();

    Destinatario destinatario();

    Integer valor();

    @JsonProperty("data_vencimento")
    Instant dataVencimento();

    @JsonProperty("data_criacao")
    Instant dataCriacao();

    EnumStatusCobranca status();

    @JsonProperty("links_pagamento")
    List<LinkPagamentoPreviewModel> linksPagamento();

}
