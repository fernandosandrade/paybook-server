package org.paybook.com.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CollectionPathTest {

    @Test
    void collectionPathJustPathTest() {
        String path = CollectionPath.of("cobranca").getPath();
        assertEquals("cobranca", path);
    }

    @Test
    void collectionPathMultiplesPathsTest() {
        String path = CollectionPath.of("cobranca", "101", "111").getPath();
        assertEquals("cobranca/101/111", path);
    }

    @Test
    void concatenateConnectionPathWithPathTest() {
        String path = CollectionPath.of("cobranca", "101", "111").getPath();
        String anotherPath = "another";
        assertEquals("cobranca/101/111/another", CollectionPath.of(path, anotherPath).getPath());
    }

}