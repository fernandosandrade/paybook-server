package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementa o acesso a uma <i>collection</i> do firestore
 */
@JBossLog
class FirestoreCollection implements IDocumentRepository {

    private final FirestoreService firestoreService;

    private final CollectionReference cref;

    FirestoreCollection(FirestoreService firestoreService, CollectionReference cref) {
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
    public Optional<DBDocument> findFirst(String campo, Object valor) {
        List<DBDocument> docs = new ArrayList<>();
        try {
            Stream<QueryDocumentSnapshot> stream =
                    this.cref.whereEqualTo(campo, valor).get().get().getDocuments().stream();

            docs = stream.map(doc -> DBDocument.from(doc)).collect(Collectors.toList());
        } catch (ExecutionException | InterruptedException e) {
            log.error(e);
        }
        if (docs.isEmpty()) {
            return Optional.empty();
        } else if (docs.size() > 1) {
            throw new RuntimeException("mais de um registro encontrado para o " + campo + "=" + valor);
        } else {
            return Optional.of(docs.get(0));
        }
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
