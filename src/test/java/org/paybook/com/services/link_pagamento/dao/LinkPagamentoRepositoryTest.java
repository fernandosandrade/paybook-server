package org.paybook.com.services.link_pagamento.dao;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;

import javax.inject.Inject;
import java.time.Instant;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
class LinkPagamentoRepositoryTest {

    private static final String ID_LINK = "teste";

    @Inject
    LinkPagamentoRepository linkPagamentoRepository;

//    @Test
//    void addLinkTest() throws InterruptedException, ExecutionException {
//        LinkPagamentoModel2 model = newLinkPagamentoModel();
//
//        LinkPagamentoModel2 modelPersistido = this.linkPagamentoRepository.save(model);
//        //assertNotNull(modelPersistido.getDocumentReference());
//    }


//    @Test
//    void updateLinkTest() throws InterruptedException, ExecutionException {
//        LinkPagamentoModel2 model = newLinkPagamentoModel();
//
//        LinkPagamentoModel2 modelPersistido = this.linkPagamentoRepository.save(model);
//        //assertNotNull(modelPersistido.getDocumentReference());
//        //String idFirestore = modelPersistido.getDocumentReference().getId();
//        modelPersistido.setValorPrincipal(999);
//        this.linkPagamentoRepository.save(modelPersistido);
//
//        Optional<LinkPagamentoModel2> models = this.linkPagamentoRepository.getById(ID_LINK);
//        assertTrue(models.isPresent(), "foi encontrado nenhum ou mais de um link com o id=" + ID_LINK);
//        // assertEquals(idFirestore,
//        //         models.get().getDocumentReference().getId(),
//        //"O firestoreID do documento retornado nao eh o mesmo do modelo original, ou seja, nao sao o mesmo documento
//        // .");
//        assertEquals(999, models.get().getValorPrincipal());
//    }

    @Test
    void deleteLinkTest() {
        LinkPagamentoModel model = newLinkPagamentoModel();
        LinkPagamentoModel modelPersistido = this.linkPagamentoRepository.add(model);
        //assertNotNull(modelPersistido.getDocumentReference());
    }

    private LinkPagamentoModel newLinkPagamentoModel() {
        return new LinkPagamentoModel.Builder()
                .id(ID_LINK)
                .idCobranca("id_cobranca")
                .valorPrincipal(100)
                .valorTaxas(10)
                .descricao("descricao")
                .url("https://urlteste")
                .vencimento(Instant.now())
                .status(EnumStatusLinkPagamento.WAITING_PAIMENT)
                .build();
    }

}
