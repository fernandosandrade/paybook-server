package org.paybook.com.services.link_pagamento.dao;

import org.paybook.com.services.cobranca.dao.CobrancaBaseModel;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class LinkPagamentoRepositoryFactory {

    @Inject
    Provider<LinkPagamentoRepository> provider;

    /**
     * Retorna um novo objeto de acesso aos links de pagamento especificos de uma cobranca.
     *
     * @param cobranca cobranca da qual serao obtidos os links de pagamento
     * @return
     */
    public <T extends CobrancaBaseModel> LinkPagamentoRepository from(T cobranca) {
        return this.provider
                .get()
                .rootRepository(cobranca);
    }

    /**
     * Retorna um onov objeto de acesso a todos os links de pagamento, independente da cobranca ao qual pertençam.
     *
     * @return
     */
    public LinkPagamentoRepository fromCollectionGroup() {
        return this.provider
                .get()
                .rootRepository();
    }

}
