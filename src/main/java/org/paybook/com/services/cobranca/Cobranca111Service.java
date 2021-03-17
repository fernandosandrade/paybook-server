package org.paybook.com.services.cobranca;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.EnumTipoBook;
import org.paybook.com.EnumTipoCobranca;
import org.paybook.com.RandomString;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;
import org.paybook.com.services.cobranca.dao.CobrancaRepository;
import org.paybook.com.services.link_pagamento.LinkPagamentoFactory;
import org.paybook.com.services.link_pagamento.LinkPagamentoService;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

@JBossLog
@ApplicationScoped
public class Cobranca111Service {

    @Inject
    CobrancaRepository cobrancaRepository;

    @Inject
    LinkPagamentoService linkCobrancaService;

    @PostConstruct
    public void init() {
        this.cobrancaRepository.repositorio(EnumTipoBook.B_101, EnumTipoCobranca.C_111);
    }

    public Cobranca111Model novaCobranca(String idBook, Integer valor, Instant dataVencimento,
                                         Destinatario destinatario) {
        Cobranca111Model cobranca = new Cobranca111Model.Builder()
                .idBook(idBook)
                .idCobranca(RandomString.next())
                .valor(valor)
                .dataCriacao(Instant.now())
                .status(EnumStatusCobranca.CHARGE_OPEN)
                .dataVencimento(dataVencimento)
                .destinatario(destinatario)
                .build();
        return this.registrar(cobranca);
    }

    /**
     * Retorna a cobranca especificada pelo id.
     *
     * @param idCobranca
     * @return
     */
    public Optional<Cobranca111Model> obter(String idCobranca) {
        return this.cobrancaRepository.getById(idCobranca)
                .map(dbDocument -> dbDocument.toObject(Cobranca111Model.class));
    }

    public Cobranca111Model incluir(Cobranca111Model cobrancaModel) {
        return this.cobrancaRepository.add(cobrancaModel)
                .toObject(Cobranca111Model.class);
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
            LinkPagamentoModel linkPagamento = LinkPagamentoFactory.from(cobrancaModel.valor(),
                    cobrancaModel.dataVencimento(),
                    cobrancaModel.idCobranca(),
                    "descricao que vai acompanhar este link");
            this.linkCobrancaService.add(linkPagamento);
            Cobranca111Model cobrancaModelComLink = new Cobranca111Model.Builder()
                    .from(cobrancaModel)
                    .addLinksCobranca(linkPagamento.id())
                    .build();
            this.cobrancaRepository.add(cobrancaModelComLink);
        } else {
            log.errorf("tentativa de registro de cobranca invalido. id=%s status=%s",
                    cobrancaModel.idCobranca(),
                    cobrancaModel.status().name());
        }
        return cobrancaModel;
    }

    public void pagar(Cobranca111Model cobranca111Model) {
    }

    public void cancelar(Cobranca111Model cobranca111Model) {
    }
}
