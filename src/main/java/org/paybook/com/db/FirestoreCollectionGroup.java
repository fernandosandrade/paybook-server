package org.paybook.com.db;

import com.google.cloud.firestore.Query;
import lombok.extern.jbosslog.JBossLog;

import java.util.List;
import java.util.Optional;

/**
 * IMplementa o acesso a um <i>collection group</i> do firestore
 */
@JBossLog
class FirestoreCollectionGroup implements IDocumentRepository {

    private final FirestoreService firestoreService;

    private final Query query;

    FirestoreCollectionGroup(FirestoreService firestoreService, Query query) {
        this.firestoreService = firestoreService;
        this.query = query;
    }

    @Override
    public DBDocument save(DBDocument document) {
        return null;
    }

    @Override
    public List<DBDocument> saveAll(List<DBDocument> documents) {
        return null;
    }

    @Override
    public Optional<DBDocument> findFirst(String campo, Object valor) {
        return Optional.empty();
    }

    @Override
    public List<DBDocument> findAll(String campo, Object valor) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(String campo, Object valor) {
        return 0;
    }

    @Override
    public long delete(String idCobranca) {
        return 0;
    }

    @Override
    public long deleteAll() {
        return 0;
    }
}
