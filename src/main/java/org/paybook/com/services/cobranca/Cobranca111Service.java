package org.paybook.com.services.cobranca;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;
import org.paybook.com.services.cobranca.dao.CobrancaRepository;
import org.paybook.com.services.link_pagamento.LinkPagamento;
import org.paybook.com.services.link_pagamento.LinkPagamentoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@JBossLog
@ApplicationScoped
public class Cobranca111Service {

    @Inject
    CobrancaRepository cobrancaRepository;

    @Inject
    LinkPagamentoService linkCobrancaService;

    public Cobranca111Service() {
        this.cobrancaRepository.repositorio(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
    }

    public Optional<Cobranca111Model> obter(String idCobranca) {
        return this.cobrancaRepository.getById(idCobranca)
                .map(dbDocument -> dbDocument.toObject(Cobranca111Model.class));
    }

    /**
     * Registra uma cobranca ja cadastrada. O registro consiste em gera o links de pagamento, dando inicio ao processo
     * de cobranca.<p> Apenas cobrancas em aberto {@link EnumStatusCobranca#CHARGE_OPEN} podem ser registradas.<br> Na
     * cobranca 111, o link gerado no registro corresponde ao valor total da cobranca, e com vencimento no mesmo dia da
     * cobranca.
     *
     * @param cobrancaModel
     * @return
     */
    public Cobranca111Model registrar(Cobranca111Model cobrancaModel) {
        if (cobrancaModel.status() == EnumStatusCobranca.CHARGE_OPEN) {
            LinkPagamento linkPagamento = LinkPagamento.from(cobrancaModel.valor(),
                    cobrancaModel.dataVencimento(),
                    cobrancaModel.idCobranca(),
                    "descricao que vai acompanhar este link");
            this.linkCobrancaService.add(linkPagamento);
            Cobranca111Model cobrancaModelComLink = new Cobranca111Model.Builder()
                    .from(cobrancaModel)
                    .addLinksCobranca(linkPagamento.getId())
                    .build();

            //JsonWrapper.fromObject()

            this.cobrancaRepository.add(cobrancaModelComLink);
        } else {
            log.errorf("tentativa de registro de cobranca invalido. id=%s status=%s",
                    cobrancaModel.idCobranca(),
                    cobrancaModel.status().name());
        }
        return cobrancaModel;
    }

}
