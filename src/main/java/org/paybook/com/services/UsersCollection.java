package org.paybook.com.services;

import org.paybook.com.db.RepositoryCollection;

public class UsersCollection {

    static final String collectionName = "users";

    private final String userId;

    protected UsersCollection(String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return RepositoryCollection.of(collectionName, this.userId).getPath();
    }
}
