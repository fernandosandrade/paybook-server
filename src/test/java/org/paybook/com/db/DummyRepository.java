package org.paybook.com.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@RequestScoped
//@IfBuildProfile("test")
public class DummyRepository implements IDocumentRepository {

    /**
     * Map contendo um documento de cobranca, indexado por um id
     */
    public List<Map<String, Object>> dados = new ArrayList();

    public void addDados(Map<String, Object> dados) {
        this.dados.add(dados);
    }

    @Override
    public DummyRepository collectionPath(String... path) {
        return this;
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
        return this.dados
                .stream()
                .filter(map -> map.containsKey(campo) && map.get(campo).equals(valor))
                .findFirst()
                .map(entry -> DBDocument.from(entry, null));
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
