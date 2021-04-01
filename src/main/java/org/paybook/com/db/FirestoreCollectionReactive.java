package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementa o acesso a uma <i>collection</i> do firestore de forma reativa
 */
@JBossLog
class FirestoreCollectionReactive implements IReactiveDocumentRepository {

    private final FirestoreService firestoreService;

    private final CollectionReference cref;

    FirestoreCollectionReactive(FirestoreService firestoreService, CollectionReference cref) {
        this.firestoreService = firestoreService;
        this.cref = cref;
    }

    @Override
    @SneakyThrows
    public DBDocument save(DBDocument document) {
        if (Objects.isNull(document.getReference())) {
            DocumentReference reference = this.cref.add(document.getData()).get();
            return DBDocument.from(reference.get().get());
        } else {
            document.getReference().update(document.getData());
            return document;
        }
    }

    @Override
    public List<DBDocument> saveAll(List<DBDocument> documents) {
        return null;
    }

    @Override
    public Uni<Optional<DBDocument>> findFirst(String campo, Object valor) {
        return Uni.createFrom().item(this.cref.whereEqualTo(campo, valor).get())
                .onItem().transform(Unchecked.function(i -> i.get().getDocuments()))
                .map(docsList -> docsList.stream().map(doc -> DBDocument.from(doc)).collect(Collectors.toList()))
                .map(dbDocs -> Optional.ofNullable(dbDocs.get(0)));
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
