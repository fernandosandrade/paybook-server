package org.paybook.com.services.cobranca;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.db.BatchWriter;
import org.paybook.com.db.DBDocument;
import org.paybook.com.services.ChargesCollection;
import org.paybook.com.services.PaymentLinksCollection;
import org.paybook.com.services.cobranca.dao.Charge111Model;
import org.paybook.com.services.cobranca.dao.ChargeRepository;
import org.paybook.com.services.link_pagamento.LinkPagamentoFactory;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;
import org.paybook.com.services.link_pagamento.dao.PaymentLinksRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;

@JBossLog
@RequestScoped
public class Charge111Service {

    @Inject
    BatchWriter batch;

    @Inject
    ChargeRepository chargeRepository;

    @Inject
    PaymentLinksRepository paymentLinkRepository;

    /**
     * Returns a Optional of {@code Cobranca111Model}. If not find, return {@code Optional.empty}
     *
     * @param idCobranca
     * @return
     */
    public Uni<Optional<Charge111Model>> find(String idCobranca, ChargesCollection chargesCollection) {
        return this.chargeRepository.onCollection(chargesCollection).getById(idCobranca)
                .map(opt -> opt.map(dbDocument -> dbDocument.toObject(Charge111Model.class)));
    }

    /**
     * Generate payment links for this charge
     * <p>
     * This operation consist in generate payment links for the specified charge. The charge will be populated with a
     * summary of the generated link and the full link will be persisted in the repository
     * </p>
     *
     * @param cobranca
     * @param chargesCollection
     * @return charge with
     */
    public Uni<Charge111Model> generatePaymentLinks(Charge111Model cobranca,
                                                    ChargesCollection chargesCollection) {
        log.infof("generating payment links for charge id [%s]", cobranca.documentID());

        var linkPagamentoModel = LinkPagamentoFactory.from(cobranca.amount(),
                cobranca.expirationDate(),
                cobranca.documentID(),
                "descricao para o link");

        var linkPagamentoPreviewModel = LinkPagamentoPreviewModel.from(linkPagamentoModel);

        Charge111Model charge111Model = new Charge111Model.Builder().from(cobranca)
                .addPaymentLinks(linkPagamentoPreviewModel)
                .status(EnumChargeStatus.WAITING_PAYMENT)
                .build();

        return persistWithBatch(chargesCollection, charge111Model, linkPagamentoModel);
    }

    /**
     * Persist cobranca and payment link in batch mode.
     * <p>
     * Returns a Uni with de charge
     * </p>
     *
     * @param chargesCollection
     * @param charge111Model
     * @param linkPagamentoModel
     * @return
     */
    protected Uni<Charge111Model> persistWithBatch(ChargesCollection chargesCollection,
                                                   Charge111Model charge111Model,
                                                   LinkPagamentoModel linkPagamentoModel) {
        DBDocument cobrancaDocument = this.chargeRepository.onCollection(chargesCollection)
                .add(charge111Model, this.batch);

        PaymentLinksCollection paymentLinksCollection = PaymentLinksCollection.from(chargesCollection,
                charge111Model.documentID());
        this.paymentLinkRepository.onCollection(paymentLinksCollection).add(linkPagamentoModel, this.batch);
//        this.linkPagamentoRepositoryFactory.from(cobrancaDocument).add(linkPagamentoModel, this.batch);
        return Uni.createFrom().item(() -> {
            this.batch.commit();
            return charge111Model;
        });
    }
}
