package org.paybook.com.db;

import com.google.cloud.firestore.*;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementa o acesso a uma <i>collection</i> do firestore
 */
@JBossLog
class FirestoreCollection implements IDocumentRepository {

    private final FirestoreService firestoreService;

    private final CollectionReference collectionReference;

    private FirestoreCollection(FirestoreService firestoreService,
                                CollectionReference collectionReference) {
        this.firestoreService = firestoreService;
        this.collectionReference = collectionReference;
    }

    static FirestoreCollection of(FirestoreService firestoreService,
                                  CollectionReference collectionReference) {
        return new FirestoreCollection(firestoreService, collectionReference);
    }

    @Override
    @SneakyThrows
    public Uni<DBDocument> save(DBDocument document) {
        return Uni.createFrom().item(() -> {
            this.persist(document.data(), document.documentReference());
            return document;
        });
    }

    @Override
    public DBDocument save(DBDocument document, BatchWriter batch) {
        batch.set(document.documentReference(), document.data());
        return document;
    }

    @Override
    public Multi<DBDocument> saveAll(List<DBDocument> documents) {
        return null;
    }

    @Override
    public Uni<Optional<DBDocument>> findByID(String documentID) {
        return Uni.createFrom()
                .item(() -> this.query(this.collectionReference.document(documentID)));
    }

    @Override
    public Uni<Optional<DBDocument>> findFirst(String campo, Object valor) {
        return Uni.createFrom()
                .item(() -> this.query(this.collectionReference.whereEqualTo(campo, valor)))
                .onItem().transform(docsList -> docsList.isEmpty() ? Optional.empty() : Optional.of(docsList.get(0)));
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

    private Optional<DBDocument> query(DocumentReference document) {
        try {
            DocumentSnapshot doc = document.get().get();
            return doc.exists() ? Optional.of(DBDocument.from(doc)) : Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<DBDocument> query(Query query) {
        try {
            List<QueryDocumentSnapshot> documents = query.get().get().getDocuments();
            List<DBDocument> collect = documents.stream()
                    .map(doc -> DBDocument.from(doc))
                    .collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(Map<String, Object> data, String documentReference) {
        try {
            this.firestoreService.get()
                    .document(documentReference)
                    .set(data, SetOptions.merge())
                    .get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
