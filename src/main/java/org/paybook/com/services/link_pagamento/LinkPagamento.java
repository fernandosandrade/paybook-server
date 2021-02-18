package org.paybook.com.services.link_pagamento;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.RandomString;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel2;

import java.time.Instant;

@JBossLog
public class LinkPagamento {

    @Getter(value = AccessLevel.PACKAGE)
    @Delegate(types = LinkPagamentoModel2.class)
    private LinkPagamentoModel2 model;

    LinkPagamento(String id,
                  String foreignId,
                  Integer valor,
                  String descricao,
                  String url,
                  Instant expiration,
                  EnumStatusLinkPagamento status) {
        this.model = new LinkPagamentoModel2(id, foreignId, valor, 0, descricao, url, expiration, status);
    }

    /**
     * Retorna um novo link de pagamento.
     *
     * @param valor
     * @param foreignId
     * @param descricao
     * @return
     */
    public static LinkPagamento from(Integer valor, Instant dataExpiracao, String foreignId, String descricao) {
        var url = "https://paybook/cobranca?id=123456789";
        var id = RandomString.next();
        return new LinkPagamento(id,
                foreignId,
                valor,
                descricao,
                url,
                dataExpiracao,
                EnumStatusLinkPagamento.WAITING_PAIMENT);
    }

}
