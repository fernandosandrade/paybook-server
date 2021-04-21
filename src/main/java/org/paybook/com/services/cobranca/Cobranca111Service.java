package org.paybook.com.services.cobranca;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;
import org.paybook.com.services.cobranca.dao.CobrancaRepository;
import org.paybook.com.services.cobranca.dao.CobrancaRepositoryFactory;
import org.paybook.com.services.link_pagamento.LinkPagamentoFactory;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoPreviewModel;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoRepositoryFactory;
import org.paybook.com.utils.RandomString;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

@JBossLog
@ApplicationScoped
public class Cobranca111Service {

    @Inject
    CobrancaRepositoryFactory cobrancaRepositoryFactory;

    @Inject
    LinkPagamentoRepositoryFactory linkPagamentoRepositoryFactory;

    CobrancaRepository cobrancaRepository;

    @PostConstruct
    public void init() {
        this.cobrancaRepository = this.cobrancaRepositoryFactory.from(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
    }

    /**
     * Creates a new {@link Cobranca111Model}
     *
     * @param idBook
     * @param valor
     * @param dataVencimento
     * @param destinatario
     * @param descricao
     * @return
     */
    public Uni<Cobranca111Model> create(String idBook, Integer valor,
                                        Instant dataVencimento,
                                        Destinatario destinatario,
                                        String descricao) {
        log.infof("create Cobranca111 for [id_book=%s, valor=%s, data_vencimento=%s, destinatario=%s]",
                idBook, valor, dataVencimento, destinatario);

        final String idCobranca = generateIdCobranca();

        LinkPagamentoModel linkPagamentoModel = LinkPagamentoFactory.from(valor,
                dataVencimento,
                idCobranca,
                descricao);

        LinkPagamentoPreviewModel linkPagamentoPreviewModel = LinkPagamentoPreviewModel.from(linkPagamentoModel);

        Cobranca111Model cobrancaModel = new Cobranca111Model.Builder()
                .documentID(idCobranca)
                .idBook(idBook)
                .valor(valor)
                .dataCriacao(Instant.now())
                .status(EnumStatusCobranca.WAITING_PAYMENT)
                .dataVencimento(dataVencimento)
                .destinatario(destinatario)
                .addLinksPagamento(linkPagamentoPreviewModel)
                .build();

        return this.cobrancaRepository.add(cobrancaModel)
                .onItem().transformToUni(dbDocument -> this.linkPagamentoRepositoryFactory.from(dbDocument)
                        .add(linkPagamentoModel))
                .map(ignored -> cobrancaModel);
    }

    /**
     * @param idCobranca
     * @return
     */
    public Uni<Optional<Cobranca111Model>> find(String idCobranca) {
        return this.cobrancaRepository.getByIdReactive(idCobranca)
                .map(opt -> opt.map(dbDocument -> dbDocument.toObject(Cobranca111Model.class)));
    }

    /**
     * Registra uma cobranca ja cadastrada. O registro consiste em gera o links de pagamento, dando inicio ao processo
     * de cobranca.
     * <p>
     * Apenas cobrancas em aberto {@link EnumStatusCobranca#CHARGE_OPEN} podem ser registradas.
     * </p>
     * <p>
     * Na cobranca 111, o link gerado no registro corresponde ao valor total da cobranca, e com vencimento no mesmo dia
     * da cobranca.
     * </p>
     *
     * @param cobrancaModel
     * @return
     */
    public Cobranca111Model registrar(Cobranca111Model cobrancaModel) {
        if (cobrancaModel.status() != EnumStatusCobranca.CHARGE_OPEN) {
            log.errorf("tentativa de registro de cobranca invalido. id=%s status=%s",
                    cobrancaModel.documentID(),
                    cobrancaModel.status().name());
        } else {
            LinkPagamentoModel linkPagamento = LinkPagamentoFactory.from(cobrancaModel.valor(),
                    cobrancaModel.dataVencimento(),
                    cobrancaModel.documentID(),
                    "descricao que vai acompanhar este link");
            LinkPagamentoPreviewModel linkPagamentoPreviewModel = LinkPagamentoPreviewModel.from(linkPagamento);

            Cobranca111Model cobrancaModelComLink = new Cobranca111Model.Builder()
                    .from(cobrancaModel)
                    .addLinksPagamento(linkPagamentoPreviewModel)
                    .status(EnumStatusCobranca.WAITING_PAYMENT)
                    .build();

            this.cobrancaRepository.add(cobrancaModelComLink);
//            this.linkPagamentoRepositoryFactory.from(cobrancaModelComLink).add(linkPagamento);
        }
        return cobrancaModel;
    }

    public void pagar(Cobranca111Model cobranca111Model) {
    }

    public void cancelar(Cobranca111Model cobranca111Model) {
    }

    private static String generateIdCobranca() {
        return RandomString.next();
    }
}
