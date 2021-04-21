package org.paybook.com.services.link_pagamento.dao;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.paybook.com.db.RepositoryMap;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import java.time.Instant;

/**
 * Classe que define um preview do link de pagamento, mostrando apenas algumas informações.
 */
@Getter
@EqualsAndHashCode
@ToString
@Builder
@Jacksonized
public class LinkPagamentoPreviewModel implements RepositoryMap {

    /**
     * link ID originator.
     */
    private String id;

    private Integer valor;

    private Instant vencimento;

    private EnumStatusLinkPagamento status;

    public static LinkPagamentoPreviewModel from(LinkPagamentoModel linkPagamentoModel) {
        return LinkPagamentoPreviewModel.builder()
                .id(linkPagamentoModel.documentID())
                .valor(linkPagamentoModel.valorPrincipal())
                .vencimento(linkPagamentoModel.vencimento())
                .status(linkPagamentoModel.status())
                .build();
    }

}
