package org.paybook.com.db;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JBossLog
@Dependent
//@DefaultBean
public class FirestoreRepository implements IDocumentRepository {

    private CollectionReference cref;

    @Inject
    FirestoreService firestoreService;

    @Override
    public FirestoreRepository collectionPath(String... path) {
        this.cref = this.firestoreService.get().collection(String.join("/", path));
        return this;
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

    /*
     * public void get() throws InterruptedException, ExecutionException { CollectionReference
     * collectionReference = firestoreService .get() .collection(COBRANCAS_COLLECTION)
     * .document("101") .collection("111");
     *
     * ApiFuture<QuerySnapshot> apiFuture = collectionReference// .whereEqualTo("tipo", "101") .get();
     *
     * List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments(); for (DocumentSnapshot
     * document : documents) { LOGGER.info(document.getData()); } //return
     * Response.ok(documents.get(0).getData()).build(); }
     */
}
