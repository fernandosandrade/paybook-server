package org.paybook.com.services.link_pagamento;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;
import org.paybook.com.utils.RandomString;

import java.time.Instant;

/**
 * Gerador de links de pagamento
 */
@JBossLog
public abstract class LinkPagamentoFactory {

    public static LinkPagamentoModel from(Integer valor, Instant vencimento, String idCobranca, String descricao) {
        var url = "https://paybook/cobranca?id=123456789";
        var id = RandomString.next();
        return new LinkPagamentoModel.Builder()
                .documentID(id)
                .idCobranca(idCobranca)
                .valorPrincipal(valor)
                .valorTaxas(Math.round(valor / 100))
                .vencimento(vencimento)
                .status(EnumStatusLinkPagamento.WAITING_PAIMENT)
                .descricao(descricao)
                .url(url)
                .build();
    }

}
