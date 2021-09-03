package org.paybook.com.services.cobranca.dao;

import io.smallrye.mutiny.Uni;
import org.paybook.com.JsonWrapper;
import org.paybook.com.db.BatchWriter;
import org.paybook.com.db.DBDocument;
import org.paybook.com.db.IDocumentRepository;
import org.paybook.com.db.RepositoryFactory;
import org.paybook.com.services.ChargesCollection;

import javax.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
public class ChargeRepository {

    private final RepositoryFactory repositoryFactory;

    public ChargeRepository(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    public IChargeRepositoryOperations onCollection(ChargesCollection chargesCollection) {
        return new ChargeRepositoryOperations(chargesCollection.getPath(),
                this.repositoryFactory.collection(chargesCollection.getPath()));
    }

    public interface IChargeRepositoryOperations {
        <T extends ChargeBaseModel> Uni<DBDocument> add(T cobrancaModel);

        <T extends ChargeBaseModel> DBDocument add(T cobrancaModel, BatchWriter batch);

        Uni<Optional<DBDocument>> getById(String idCobranca);
    }

    class ChargeRepositoryOperations implements IChargeRepositoryOperations {

        private final IDocumentRepository documentRepository;

        private final String chargesCollection;

        ChargeRepositoryOperations(String chargesCollection, IDocumentRepository documentRepository) {
            this.documentRepository = documentRepository;
            this.chargesCollection = chargesCollection;
        }

        @Override
        public <T extends ChargeBaseModel> Uni<DBDocument> add(T cobrancaModel) {
            DBDocument dbDocument = DBDocument.from(
                    JsonWrapper.fromObject(cobrancaModel),
                    this.chargesCollection,
                    cobrancaModel.documentID());
            return this.documentRepository.save(dbDocument);
        }

        @Override
        public <T extends ChargeBaseModel> DBDocument add(T cobrancaModel, BatchWriter batch) {
            DBDocument dbDocument = DBDocument.from(
                    JsonWrapper.fromObject(cobrancaModel),
                    this.chargesCollection,
                    cobrancaModel.documentID());
            return this.documentRepository.save(dbDocument, batch);
        }

        @Override
        public Uni<Optional<DBDocument>> getById(String idCobranca) {
            return this.documentRepository.findByID(idCobranca);
        }

    }


}
