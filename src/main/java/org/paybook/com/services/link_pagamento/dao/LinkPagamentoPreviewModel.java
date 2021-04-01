package org.paybook.com.services.link_pagamento.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;

/**
 * Classe que define um preview do link de pagamento, mostrando apenas algumas informações
 */
@Getter
@Accessors(fluent = true)
@Builder
@Jacksonized
public class LinkPagamentoPreviewModel {

    private String id;

    private Integer valor;

    private Instant vencimento;

    private EnumStatusLinkPagamento status;

    public static LinkPagamentoPreviewModel from(LinkPagamentoModel linkPagamentoModel) {
        return LinkPagamentoPreviewModel.builder()
                .id(linkPagamentoModel.id())
                .valor(linkPagamentoModel.valorPrincipal())
                .vencimento(linkPagamentoModel.vencimento())
                .status(linkPagamentoModel.status())
                .build();
    }

}
