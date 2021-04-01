package org.paybook.com.db;

import java.util.List;
import java.util.Optional;

/**
 * Interface comum a repositorios de documentos
 */
public interface IDocumentRepository {

    DBDocument save(DBDocument document);

    List<DBDocument> saveAll(List<DBDocument> documents);

    Optional<DBDocument> findFirst(String campo, Object valor);

    List<DBDocument> findAll(String campo, Object valor);

    long count();

    long count(String campo, Object valor);

    long delete(String idCobranca);

    long deleteAll();


}
