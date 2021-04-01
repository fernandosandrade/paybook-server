package org.paybook.com.services.cobranca.dao;

import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class CobrancaRepositoryFactory {

    @Inject
    Provider<CobrancaRepository> cobrancaProvider;

    public CobrancaRepository from(EnumTipoBook book, EnumTipoCobranca cobranca) {
        return this.cobrancaProvider
                .get()
                .repositorio(book, cobranca);
    }

}
