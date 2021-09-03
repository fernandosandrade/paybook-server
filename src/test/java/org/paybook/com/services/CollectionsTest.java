package org.paybook.com.services;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CollectionsTest {
//    @Test
//    void usersCollectionTest() {
//        ChargesCollection collection = UsersCollection.of();
//        System.out.println(collection.getPath());
//    }

    @Test
    void booksCollectionTest() {
        BooksCollection collection = BooksCollection.of("9lSbTlfCh3UawuxgGS63F9zNAJw2");
        Assertions.assertEquals(
                "users/9lSbTlfCh3UawuxgGS63F9zNAJw2/books", collection.getPath());
    }

    @Test
    void chargesCollectionTest() {
        ChargesCollection collection = ChargesCollection.of("9lSbTlfCh3UawuxgGS63F9zNAJw2",
                "FMF92AK0AA7WNMLFV5Ol");
        Assertions.assertEquals(
                "users/9lSbTlfCh3UawuxgGS63F9zNAJw2/books/FMF92AK0AA7WNMLFV5Ol/charges", collection.getPath());
    }

    @Test
    void paymentLinksCollectionTest() {
        var collection = PaymentLinksCollection.of("9lSbTlfCh3UawuxgGS63F9zNAJw2",
                "FMF92AK0AA7WNMLFV5Ol", "47520016904330182046");
        Assertions.assertEquals(
                "users/9lSbTlfCh3UawuxgGS63F9zNAJw2/books/FMF92AK0AA7WNMLFV5Ol/charges/47520016904330182046" +
                        "/payment_links", collection.getPath());

        var chargesCollection = ChargesCollection.of("9lSbTlfCh3UawuxgGS63F9zNAJw2",
                "FMF92AK0AA7WNMLFV5Ol");
        var linksCollection = PaymentLinksCollection.from(chargesCollection,
                "47520016904330182046");
        Assertions.assertEquals("users/9lSbTlfCh3UawuxgGS63F9zNAJw2/books/FMF92AK0AA7WNMLFV5Ol/charges" +
                "/47520016904330182046/payment_links", linksCollection.getPath());
    }
}
