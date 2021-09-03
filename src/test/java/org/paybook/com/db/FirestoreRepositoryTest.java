package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@QuarkusTest
class FirestoreRepositoryTest {

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


}