package org.paybook.com.services.cobranca.dao;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.CollectionPath;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.RepositoryFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@JBossLog
@Dependent
public class CobrancaRepository {

    static final String COBRANCAS_COLLECTION = "cobrancas";

    static final String CAMPO_ID_COBRANCA = "id_cobranca";

    @Inject
    RepositoryFactory repositoryFactory;

    private IDocumentRepository documentRepository;

    private CollectionPath collectionPath;

    CobrancaRepository setRepository(EnumTipoBook book, EnumTipoCobranca cobranca) {
        this.collectionPath = CollectionPath.of(COBRANCAS_COLLECTION,
                book.getValorAsString(),
                cobranca.getValorAsString());
        log.infof("new cobranca repository for [%s]", this.collectionPath.getPath());
        this.documentRepository = this.repositoryFactory.collection(this.collectionPath);
        return this;
    }

    public Uni<Optional<DBDocument>> getById(String idCobranca) {
        return this.documentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca);
    }

    public Uni<Optional<DBDocument>> getByIdReactive(String idCobranca) {
        return this.documentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca);
    }

    /**
     * @param <T>
     * @param cobrancaModel
     * @return
     */
    public <T extends CobrancaBaseModel> Uni<DBDocument> add(T cobrancaModel) {
        DBDocument dbDocument = DBDocument.from(
                JsonWrapper.fromObject(cobrancaModel),
                this.collectionPath,
                cobrancaModel.documentID());
        return this.documentRepository.save(dbDocument);
    }

    /**
     * Faz o update do documento
     *
     * @param cobrancaModel
     * @param <T>
     */
    public <T extends CobrancaBaseModel> Uni<DBDocument> update(T cobrancaModel) {
        Objects.requireNonNull(cobrancaModel.documentID(), "referencia do documento nao pode ser nula");
        DBDocument dbDocument = DBDocument.from(
                JsonWrapper.fromObject(cobrancaModel),
                this.collectionPath,
                cobrancaModel.documentID());
        return this.documentRepository.save(dbDocument);
    }

}
