package org.paybook.com.db;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static io.restassured.RestAssured.given;

@QuarkusTest
class FirestoreCollectionReactiveTest {


    @Inject
    RepositoryFactory repositoryFactory;

    @Test
    void teste() {
        IReactiveDocumentRepository repository = this.repositoryFactory.reactiveCollection(
                "cobrancas/101/111/");
        Uni<Optional<DBDocument>> first = repository.findFirst("id_book", "teste");
        first.map(opt -> opt.isEmpty() ? null : opt.get()).subscribe().with(System.out::println);
    }

    @Test
    void testRest() {
        given()
                .when().get("/v1/cobranca/101/111/reactive/teste")
                .then()
                .statusCode(200)
                .extract().body().asString();
    }

}