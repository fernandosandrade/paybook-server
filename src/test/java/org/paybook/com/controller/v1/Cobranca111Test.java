package org.paybook.com.controller.v1;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.DummyRepository;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;

import javax.enterprise.context.ApplicationScoped;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class Cobranca111Test {


    @Test
    public void registroTeste() {
        given()
                .when().get("/v1/cobranca/101/111/1")
                .then()
                .statusCode(200)
                .body(is("Hello RESTEasy"));
    }

    @ApplicationScoped
    @IfBuildProfile("test")
    public static class CobrancaDummyRepository extends DummyRepository {

        public CobrancaDummyRepository() {
            Cobranca111Model cobrancaModel = new Cobranca111Model.Builder()
                    .idCobranca("1")
                    .build();
            super.addDados(JsonWrapper.fromObject(cobrancaModel));
        }

    }

}
