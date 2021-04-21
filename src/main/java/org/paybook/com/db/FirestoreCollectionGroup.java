package org.paybook.com.db;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import java.util.List;
import java.util.Optional;

/**
 * IMplementa o acesso a um <i>collection group</i> do firestore
 */
@JBossLog
class FirestoreCollectionGroup implements IDocumentRepository {

    private final FirestoreService firestoreService;

    private final String collectionGroup;

    private FirestoreCollectionGroup(FirestoreService firestoreService, String collectionGroup) {
        this.firestoreService = firestoreService;
        this.collectionGroup = collectionGroup;
    }

    static IDocumentRepository of(FirestoreService firestoreService, String collectionGroup) {
        return new FirestoreCollectionGroup(firestoreService, collectionGroup);
    }

    @Override
    public Uni<DBDocument> save(DBDocument document) {
        return null;
    }

    @Override
    public Multi<DBDocument> saveAll(List<DBDocument> documents) {
        return null;
    }

    @Override
    public Uni<Optional<DBDocument>> findFirst(String campo, Object valor) {
        return Uni.createFrom().item(Optional.empty());
    }

    @Override
    public Multi<DBDocument> findAll(String campo, Object valor) {
        return null;
    }

    @Override
    public Uni<Long> count() {
        return Uni.createFrom().item(0L);
    }

    @Override
    public Uni<Long> count(String campo, Object valor) {
        return Uni.createFrom().item(0L);
    }

    @Override
    public Uni<Boolean> delete(String idCobranca) {
        return Uni.createFrom().item(false);
    }

    @Override
    public Uni<Long> deleteAll() {
        return Uni.createFrom().item(0L);
    }
}
