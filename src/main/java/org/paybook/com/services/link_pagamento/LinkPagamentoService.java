package org.paybook.com.services.link_pagamento;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.exception.EntityType;
import org.paybook.com.exception.ExceptionFactory;
import org.paybook.com.exception.ExceptionType;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoRepository;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoRepositoryFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipulacao de links de pagamento. Fornece as operacoes disponíveis para um link, validando regras de
 * negocio.
 */
@JBossLog
@ApplicationScoped
public class LinkPagamentoService {

    @Inject
    LinkPagamentoRepositoryFactory linkPagamentoRepositoryFactory;

    private LinkPagamentoRepository linkPagamentoRepository;

    @PostConstruct
    void init() {
        this.linkPagamentoRepository = this.linkPagamentoRepositoryFactory.fromCollectionGroup();
    }

    public Uni<LinkPagamentoModel> add(LinkPagamentoModel linkPagamento) {
        return this.linkPagamentoRepository.getById(linkPagamento.documentID())
                .onItem().transformToUni(opt -> {
                    if (opt.isEmpty()) {
                        RuntimeException exception = exception(EntityType.LINK_PAGAMENTO,
                                ExceptionType.DUPLICATE_ENTITY,
                                linkPagamento.documentID());
                        return Uni.createFrom().failure(exception);
                    }
                    return this.linkPagamentoRepository.add(linkPagamento);
                });
    }

    public LinkPagamentoModel update(LinkPagamentoModel linkPagamento) {
        return null;
    }

    public void delete(LinkPagamentoModel linkPagamento) {
    }

    public Optional<LinkPagamentoModel> getById(String idLinkPagamento) {
        return Optional.empty();
    }

    /**
     * Retorna todos os link de pagamento associados a uma cobrança.
     *
     * @param idCobranca id da cobranca pai dos links de pagamento
     * @return lista contendo os links de pagamento
     */
    public List<LinkPagamentoModel> getAllByCobranca(String idCobranca) {
        return Collections.emptyList();
    }

    /**
     * Registra o pagamento da cobranca.
     *
     * @param linkPagamento
     */
    public void payLink(LinkPagamentoModel linkPagamento) {
    }

    /**
     * Seta o link como pago de forma manual.
     *
     * @param linkPagamento
     */
    public void setLinkAsPaid(LinkPagamentoModel linkPagamento) {
    }

    /**
     * Cancela o link.
     * <p>
     * Deve ser utilizado quando o cancelamento se da de forma sistema e automatica, como por exemplo, o cancelamento da
     * cobranca que gerou o link.
     * </p>
     *
     * @param linkPagamento
     */
    public void cancelLink(LinkPagamentoModel linkPagamento) {
    }

    /**
     * Cancela o link, mas com a origem do evento sendo
     *
     * @param linkPagamento
     */
    public void setLinkAsCanceled(LinkPagamentoModel linkPagamento) {
    }

    public void expireLink(LinkPagamentoModel linkPagamento) {
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ExceptionFactory.throwException(entityType, exceptionType, args);
    }
}
