package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@JBossLog
@QuarkusTest
class FirestoreCollectionReactiveTest {

    @Inject
    FirestoreService firestoreService;

    @Inject
    RepositoryFactory repositoryFactory;

    private CollectionReference collection;

    @Test
    void testRest() {
        String s = given()
                .when().get("/v1/cobranca/101/111/reactive/teste")
                .then()
                .statusCode(200)
                .extract().body().asString();
        System.out.println(s);
    }

    @Test
    public void findFirst() {
        this.collection = this.firestoreService.get().collection("cobrancas/101/111/");
        String campo = "id_book";
        String valor = "teste4";

        Uni.createFrom()
                .item(() -> getDocuments(this.collection.whereEqualTo(campo, valor)))
                .onItem().transform(docsList -> docsList.isEmpty() ? Optional.empty() : Optional.of(docsList.get(0)))
                .onFailure().invoke(e -> log.errorf("===>>> erro na base [%s]", e.getMessage()))
                .onTermination().invoke(() -> System.out.println("===>>> terminou"))
                .onSubscribe().invoke(() -> System.out.println("===>>> subscribe"))
                .subscribe().with(System.out::println);
    }

    List<DBDocument> getDocuments(Query query) {
        try {
            List<QueryDocumentSnapshot> documents = query.get().get().getDocuments();
            List<DBDocument> collect = documents.stream()
                    .map(doc -> DBDocument.from(doc))
                    .collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void teste4() {

//        FirestoreOperations mockRepo = Mockito.mock(FirestoreOperations.class);
//
//        Mockito.when(mockRepo.query(any())).thenReturn(List.of());
//        QuarkusMock.installMockForType(mockRepo, FirestoreOperations.class);
//
//        IDocumentRepository repository = this.repositoryFactory.collection(
//                CollectionPath.of("cobrancas", "101", "111"));
//        repository.findFirst("id_book", "teste")
//                .subscribe()
//                .with(System.out::println);
    }

    interface ExceptionSupplier<T> {
        T get() throws Exception;
    }


}