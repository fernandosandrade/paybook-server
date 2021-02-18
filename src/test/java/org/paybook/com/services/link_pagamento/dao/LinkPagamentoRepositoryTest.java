package org.paybook.com.services.link_pagamento.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
class LinkPagamentoRepositoryTest {

    private static final String MODEL_ID = "teste";
    @Inject
    LinkPagamentoRepository linkPagamentoRepository;

    @BeforeEach
    void wipeData() {
        this.linkPagamentoRepository.delete(MODEL_ID);
    }

    @Test
    void addLinkTest() throws InterruptedException, ExecutionException {
        LinkPagamentoModel2 model = newLinkPagamentoModel();

        LinkPagamentoModel2 modelPersistido = this.linkPagamentoRepository.save(model);
        //assertNotNull(modelPersistido.getDocumentReference());
    }


    @Test
    void updateLinkTest() throws InterruptedException, ExecutionException {
        LinkPagamentoModel2 model = newLinkPagamentoModel();

        LinkPagamentoModel2 modelPersistido = this.linkPagamentoRepository.save(model);
        //assertNotNull(modelPersistido.getDocumentReference());
        //String idFirestore = modelPersistido.getDocumentReference().getId();
        modelPersistido.setValorPrincipal(999);
        this.linkPagamentoRepository.save(modelPersistido);

        Optional<LinkPagamentoModel2> models = this.linkPagamentoRepository.getById(MODEL_ID);
        assertTrue(models.isPresent(), "foi encontrado nenhum ou mais de um link com o id=" + MODEL_ID);
        // assertEquals(idFirestore,
        //         models.get().getDocumentReference().getId(),
        //"O firestoreID do documento retornado nao eh o mesmo do modelo original, ou seja, nao sao o mesmo documento
        // .");
        assertEquals(999, models.get().getValorPrincipal());
    }

    @Test
    void deleteLinkTest() {
        LinkPagamentoModel2 model = newLinkPagamentoModel();
        LinkPagamentoModel2 modelPersistido = this.linkPagamentoRepository.save(model);
        //assertNotNull(modelPersistido.getDocumentReference());
        this.linkPagamentoRepository.delete(MODEL_ID);
    }

    private LinkPagamentoModel2 newLinkPagamentoModel() {
        return new LinkPagamentoModel2(MODEL_ID, "teste", 100, 10, "descricao", "https://urlteste", Instant.now(),
                EnumStatusLinkPagamento.WAITING_PAIMENT);
    }

}
