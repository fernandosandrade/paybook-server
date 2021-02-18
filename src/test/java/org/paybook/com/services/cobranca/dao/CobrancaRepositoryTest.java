package org.paybook.com.services.cobranca.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;
import org.paybook.com.EnumTipoBookTest;
import org.paybook.com.EnumTipoCobrancaTest;
import org.paybook.com.JsonWrapper;
import org.paybook.com.RepositoryTestProfile;
import org.paybook.com.services.cobranca.CobrancaDocument;
import org.paybook.com.services.cobranca.CobrancaDocumentExample;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestProfile(RepositoryTestProfile.class)
class CobrancaRepositoryTest {

    private static final String MODEL_ID = "teste";

    @Inject
    CobrancaRepository cobrancaRepository;

    @Inject
    CobrancaRepository repo;

    @Test
    void testeSalvar() throws ExecutionException, InterruptedException {
        CobrancaTestModel cobranca = newCobrancaTestModel();
        final var jsonMap = JsonWrapper.fromObject(cobranca);

        final CobrancaDocument cobrancaDocument = this.cobrancaRepository.book(EnumTipoBookTest.B_TESTE)
                .cobrancas(EnumTipoCobrancaTest.C_TESTE)
                .save(CobrancaDocument.from(jsonMap));

        assertNotNull(cobrancaDocument.getReference());
        cobrancaDocument.getReference().delete().get();
    }

    @Test
    void getByIdTest() throws InterruptedException, JsonProcessingException {
        Optional<CobrancaDocument> cobranca = this.cobrancaRepository.book(EnumTipoBookTest.B_TESTE)
                .cobrancas(EnumTipoCobrancaTest.C_TESTE)
                .getById("teste");
        System.out.println(new ObjectMapper().writeValueAsString(cobranca.get().getData()));
        assertTrue(cobranca.isPresent());
    }

    private CobrancaTestModel newCobrancaTestModel() {
        final var jsonMap = CobrancaDocumentExample.newTestInstance().getData();
        return JsonWrapper.toObject(jsonMap, ImmutableCobrancaTestModel.class);
    }

}
