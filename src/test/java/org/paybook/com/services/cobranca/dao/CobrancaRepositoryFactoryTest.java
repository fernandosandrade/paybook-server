package org.paybook.com.services.cobranca.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
class CobrancaRepositoryFactoryTest {

    @Inject
    CobrancaRepositoryFactory repositoryFactory;

    /** instancias devem ser diferentes */
    @Test
    void obterInstanciasViaFactory() {
        CobrancaRepository repository1 = this.repositoryFactory.from(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
        CobrancaRepository repository2 = this.repositoryFactory.from(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
        assertNotEquals(repository1, repository2);
    }

}