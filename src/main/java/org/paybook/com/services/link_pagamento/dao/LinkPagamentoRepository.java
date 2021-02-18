package org.paybook.com.services.link_pagamento.dao;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;
import java.util.Optional;

@JBossLog
@Named("linkPagamentoRepository")
@ApplicationScoped
public class LinkPagamentoRepository {

    static final String LINK_PAGAMENTO_COLLECTION = "link_pagamento";

    static final String CAMPO_ID_COBRANCA = "id_link";

    private IDocumentRepository documentRepository;

    @Inject
    public LinkPagamentoRepository(IDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.documentRepository.collectionPath(LINK_PAGAMENTO_COLLECTION);
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
