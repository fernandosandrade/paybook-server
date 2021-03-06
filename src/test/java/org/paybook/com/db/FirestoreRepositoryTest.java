package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumStatusCobranca;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;
import org.paybook.com.services.cobranca.dao.CobrancaRepository;
import org.paybook.com.services.cobranca.dao.CobrancaRepositoryFactory;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

@QuarkusTest
class FirestoreRepositoryTest {

    @Inject
    CobrancaRepositoryFactory crFactory;

    @Inject
    FirestoreService firestoreService;

    @Test
    void test() throws ExecutionException, InterruptedException {
        String id = "O5OUe1ZKbmz5VNM9R7z4";
        CollectionReference collection = this.firestoreService.get()
                .collection("cobrancas/101/111/");
        DocumentReference document = collection.document(id);

        DocumentSnapshot documentSnapshot = document.get().get();
        DocumentReference reference = documentSnapshot.getReference();
        String path = reference.getPath(); //-> cobrancas/101/111/O5OUe1ZKbmz5VNM9R7z4
        String id1 = reference.getId(); //-> O5OUe1ZKbmz5VNM9R7z4
        String path1 = reference.getParent().getPath(); //-> cobrancas/101/111
        System.out.println(path);
        System.out.println(id1);
        System.out.println(path1);

        CollectionReference linksCobranca = document.collection("links_pagamento");
        for (DocumentReference documentReference : linksCobranca.listDocuments()) {
            System.out.println(documentReference.getId());
        }

    }

    @Test
    void teste1() {
        Cobranca111Model cobranca111Model = new Cobranca111Model.Builder()
                .idCobranca("teste")
                .idBook("id_book")
                .destinatario(new Destinatario("sr.strass@gmail.com", "fernando", "999467985"))
                .dataCriacao(Instant.now())
                .dataVencimento(Instant.now().plus(10, ChronoUnit.DAYS))
                .status(EnumStatusCobranca.WAITING_PAYMENT)
                .valor(100)
                .build();

        CobrancaRepository cobrancaRepository = this.crFactory.from(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
        CobrancaRepository cobrancaRepository2 = this.crFactory.from(EnumTipoBook.B_101, EnumTipoCobranca.C_112);
        cobrancaRepository.getById("DW0gVAwGRml7AsMN");
    }

}