package org.paybook.com.services.link_pagamento.dao;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.RepositoryFactory;
import org.paybook.com.services.cobranca.dao.CobrancaBaseModel;

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

    IDocumentRepository documentRepository;

    LinkPagamentoRepository rootRepository(CobrancaBaseModel cobranca) {
        this.documentRepository =
                this.repositoryFactory.collection(
                        Objects.requireNonNull(cobranca.documentReference(),
                                "cobranca passada nao possui referencia para um documento do repositorio.")
                                .getPath() + LINK_PAGAMENTO_COLLECTION);
        return this;
    }

    LinkPagamentoRepository rootRepository() {
        this.documentRepository = this.repositoryFactory.collectionGroup(LINK_PAGAMENTO_COLLECTION);
        return this;
    }

    public Optional<LinkPagamentoModel> getById(String idCobranca) {
        return this.documentRepository.findFirst(CAMPO_ID_COBRANCA, idCobranca)
                .map(dbDocument -> dbDocument.toObject(LinkPagamentoModel.class));
    }

    public LinkPagamentoModel add(LinkPagamentoModel linkCobrancaModel) {
        if (linkCobrancaModel.documentReference() != null) {
            throw new IllegalArgumentException(
                    "apenas documentos sem referencia podem ser adicionados. utilize a operacao de 'update' para " +
                            "atualizar um documento ja existente");
        }
        DBDocument dbDocument = DBDocument.from(JsonWrapper.fromObject(linkCobrancaModel), null);
        return this.documentRepository.save(dbDocument)
                .toObject(LinkPagamentoModel.class);
    }

    public LinkPagamentoModel update(LinkPagamentoModel linkCobrancaModel) {
        Objects.requireNonNull(linkCobrancaModel.documentReference(), "referencia do documento nao pode ser nula");
        DBDocument dbDocument = DBDocument.from(JsonWrapper.fromObject(linkCobrancaModel),
                linkCobrancaModel.documentReference());
        return this.documentRepository.save(dbDocument)
                .toObject(LinkPagamentoModel.class);
    }

}
