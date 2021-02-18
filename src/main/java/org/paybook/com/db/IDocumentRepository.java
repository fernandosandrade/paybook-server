package org.paybook.com.db;

import java.util.List;
import java.util.Optional;

public interface IDocumentRepository {

    IDocumentRepository collectionPath(String... path);

    DBDocument save(DBDocument document);

    List<DBDocument> saveAll(List<DBDocument> documents);

    Optional<DBDocument> findFirst(String campo, Object valor);

    List<DBDocument> findAll(String campo, Object valor);

    long count();

    long count(String campo, Object valor);

    long delete(String idCobranca);

    long deleteAll();


}
