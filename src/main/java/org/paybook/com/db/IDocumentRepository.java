package org.paybook.com.db;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

/**
 * Interface comum a repositorios de documentos
 */
public interface IDocumentRepository {

    /**
     * Save the document on repository.
     * <p>
     * If document does not exists, it will be created. If already exists, will be merged with informed document.
     * </p>
     *
     * @param document
     * @return
     */
    Uni<DBDocument> save(DBDocument document);

    Multi<DBDocument> saveAll(List<DBDocument> documents);

    Uni<Optional<DBDocument>> findFirst(String campo, Object valor);

    Multi<DBDocument> findAll(String campo, Object valor);

    Uni<Long> count();

    Uni<Long> count(String campo, Object valor);

    Uni<Boolean> delete(String idCobranca);

    Uni<Long> deleteAll();

}
