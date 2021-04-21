package org.paybook.com.services.link_pagamento.dao;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.CollectionPath;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.RepositoryFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 * Fornece acesso aos documentos de links de pagamento.
 * <p>
 * Deve ser sempre obtido por meio da factory {@link LinkPagamentoRepositoryFactory}.
 * </p>
 */
@JBossLog
@Dependent
public class LinkPagamentoRepository {

    static final String LINK_PAGAMENTO_COLLECTION = "links_pagamento";

    static final String CAMPO_ID_COBRANCA = "id_link";

    @Inject
    RepositoryFactory repositoryFactory;

    private IDocumentRepository documentRepository;

    private CollectionPath collectionPath;

    LinkPagamentoRepository rootRepository(DBDocument documentoCobranca) {
        Objects.requireNonNull(documentoCobranca.documentReference(),
                "cobranca passada nao possui referencia para um documento do repositorio.");
        log.infof("new link pagamento repository for [%s]", documentoCobranca.documentReference());
        this.collectionPath = CollectionPath.of(documentoCobranca.documentReference(), LINK_PAGAMENTO_COLLECTION);
        this.documentRepository = this.repositoryFactory.collection(this.collectionPath);
        return this;
    }

    LinkPagamentoRepository rootRepository() {
        this.documentRepository = this.repositoryFactory.collectionGroup(LINK_PAGAMENTO_COLLECTION);
        return this;
    }

    public Uni<Optional<LinkPagamentoModel>> getById(String idCobranca) {
        return this.documentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca)
                .map(opt -> opt.map(dbDocument -> dbDocument.toObject(LinkPagamentoModel.class)));
    }

    public Uni<LinkPagamentoModel> add(LinkPagamentoModel linkCobrancaModel) {
        DBDocument dbDocument = DBDocument.from(
                JsonWrapper.fromObject(linkCobrancaModel),
                this.collectionPath,
                linkCobrancaModel.documentID());

        return this.documentRepository.save(dbDocument)
                .onItem().transform(doc -> doc.toObject(LinkPagamentoModel.class));
    }

    public Uni<LinkPagamentoModel> update(LinkPagamentoModel linkCobrancaModel) {
        Objects.requireNonNull(linkCobrancaModel.documentID(), "id do documento nao pode ser nula");
        DBDocument dbDocument = DBDocument.from(
                JsonWrapper.fromObject(linkCobrancaModel),
                this.collectionPath,
                linkCobrancaModel.documentID());
        return this.documentRepository.save(dbDocument)
                .onItem().transform(doc -> doc.toObject(LinkPagamentoModel.class));
    }

}
