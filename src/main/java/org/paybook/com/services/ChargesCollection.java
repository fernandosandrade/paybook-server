package org.paybook.com.services;

import org.paybook.com.db.RepositoryCollection;

public class ChargesCollection {

    static final String collectionName = "charges";

    private final String chargesId;

    private final BooksCollection booksCollection;

    ChargesCollection(String userId, String booksId, String chargesId) {
        this.booksCollection = new BooksCollection(userId, booksId);
        this.chargesId = chargesId;
    }

    private ChargesCollection(BooksCollection booksCollection, String chargesId) {
        this.booksCollection = booksCollection;
        this.chargesId = chargesId;
    }

    public static ChargesCollection of(String userId, String booksId) {
        return new ChargesCollection(userId, booksId, null);
    }

    /**
     * Return new {@code ChargesCollection} built from this instance, including the informed charge id.
     *
     * @param chargeId
     * @return
     */
    ChargesCollection withChargeId(String chargeId) {
        return new ChargesCollection(this.booksCollection, chargeId);
    }

    public String getPath() {
        return this.chargesId == null ? RepositoryCollection.of(this.booksCollection.getPath(), collectionName)
                .getPath() :
                RepositoryCollection.of(this.booksCollection.getPath(), collectionName, this.chargesId).getPath();
    }

}
