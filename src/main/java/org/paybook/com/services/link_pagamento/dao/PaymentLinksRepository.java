package org.paybook.com.services.link_pagamento.dao;

import io.smallrye.mutiny.Uni;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.BatchWriter;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.RepositoryFactory;
import org.paybook.com.services.PaymentLinksCollection;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentLinksRepository {

    private final RepositoryFactory repositoryFactory;

    public PaymentLinksRepository(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    public IPaymentLinkRepositoryOperations onCollection(PaymentLinksCollection paymentLinksCollection) {
        return new PaymentLinkRepositoryOperations(paymentLinksCollection.getPath(),
                this.repositoryFactory.collection(paymentLinksCollection.getPath()));
    }

    public interface IPaymentLinkRepositoryOperations {
        Uni<LinkPagamentoModel> add(LinkPagamentoModel linkCobrancaModel);

        LinkPagamentoModel add(LinkPagamentoModel linkCobrancaModel, BatchWriter batch);
    }

    class PaymentLinkRepositoryOperations implements IPaymentLinkRepositoryOperations {

        private final String paymentLinkCollection;

        private final IDocumentRepository documentRepository;

        PaymentLinkRepositoryOperations(String paymentLinkCollection,
                                        IDocumentRepository documentRepository) {
            this.paymentLinkCollection = paymentLinkCollection;
            this.documentRepository = documentRepository;
        }

        @Override
        public Uni<LinkPagamentoModel> add(LinkPagamentoModel linkCobrancaModel) {
            DBDocument dbDocument = DBDocument.from(
                    JsonWrapper.fromObject(linkCobrancaModel),
                    this.paymentLinkCollection,
                    linkCobrancaModel.documentID());

            return this.documentRepository.save(dbDocument)
                    .onItem().transform(doc -> doc.toObject(LinkPagamentoModel.class));
        }

        @Override
        public LinkPagamentoModel add(LinkPagamentoModel linkCobrancaModel, BatchWriter batch) {
            DBDocument dbDocument = DBDocument.from(
                    JsonWrapper.fromObject(linkCobrancaModel),
                    this.paymentLinkCollection,
                    linkCobrancaModel.documentID());

            return this.documentRepository.save(dbDocument, batch)
                    .toObject(LinkPagamentoModel.class);
        }
    }


}
