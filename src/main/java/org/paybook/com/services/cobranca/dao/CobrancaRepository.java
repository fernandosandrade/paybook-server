package org.paybook.com.services.cobranca.dao;

import io.smallrye.mutiny.Uni;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.IReactiveDocumentRepository;
import org.paybook.com.db.RepositoryFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

//@ApplicationScoped
@Dependent
public class CobrancaRepository {

    static final String COBRANCAS_COLLECTION = "cobrancas";

    static final String CAMPO_ID_COBRANCA = "id_cobranca";

    @Inject
    RepositoryFactory repositoryFactory;

    private IDocumentRepository documentRepository;

    private IReactiveDocumentRepository reactiveDocumentRepository;

    CobrancaRepository repositorio(EnumTipoBook book, EnumTipoCobranca cobranca) {
        this.documentRepository = this.repositoryFactory.collection(COBRANCAS_COLLECTION,
                book.getValorAsString(),
                cobranca.getValorAsString());

        this.reactiveDocumentRepository = this.repositoryFactory.reactiveCollection(COBRANCAS_COLLECTION,
                book.getValorAsString(),
                cobranca.getValorAsString());
        return this;
    }

    public Optional<DBDocument> getById(String idCobranca) {
        return this.documentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca);
    }

    public Uni<Optional<DBDocument>> getByIdReactive(String idCobranca) {
        return this.reactiveDocumentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca);
    }

    /**
     * @param <T>
     * @param cobrancaModel
     * @return
     */
    public <T extends CobrancaBaseModel> DBDocument add(T cobrancaModel) {
        if (cobrancaModel.documentReference() != null) {
            throw new IllegalArgumentException(
                    "apenas documentos sem referencia podem ser adicionados. utilize a operacao de 'update' para " +
                            "atualizar um documento ja existente");
        }
        DBDocument dbDocument = DBDocument.from(JsonWrapper.fromObject(cobrancaModel), null);
        return this.documentRepository.save(dbDocument);
    }

    /**
     * Faz o update do documento
     *
     * @param cobrancaModel
     * @param <T>
     */
    public <T extends CobrancaBaseModel> void update(T cobrancaModel) {
        Objects.requireNonNull(cobrancaModel.documentReference(), "referencia do documento nao pode ser nula");
        DBDocument dbDocument = DBDocument.from(JsonWrapper.fromObject(cobrancaModel),
                cobrancaModel.documentReference());
        this.documentRepository.save(dbDocument);
    }

}
