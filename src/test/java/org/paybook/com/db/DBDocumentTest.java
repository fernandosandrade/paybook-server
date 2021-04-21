package org.paybook.com.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class DBDocumentTest {

    @Test
    void getIDandCollectionPathTest() {
        DBDocument dbDocument = DBDocument.from(Map.of(),
                CollectionPath.of("cobranca", "101", "111"),
                "123456");

        assertEquals("cobranca/101/111/123456", dbDocument.documentReference());
        assertEquals("cobranca/101/111", dbDocument.collection());
        assertEquals("123456", dbDocument.id());
    }

}