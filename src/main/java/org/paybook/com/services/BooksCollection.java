package org.paybook.com.services;

import org.paybook.com.db.RepositoryCollection;

public class BooksCollection extends UsersCollection {

    static final String collectionName = "books";

    private final String booksId;

    protected BooksCollection(String userId, String booksId) {
        super(userId);
        this.booksId = booksId;
    }

    public static BooksCollection of(String userId) {
        return new BooksCollection(userId, null);
    }

    @Override
    public String getPath() {
        return this.booksId == null ? RepositoryCollection.of(super.getPath(), collectionName).getPath() :
                RepositoryCollection.of(super.getPath(), collectionName, this.booksId).getPath();
    }
}
