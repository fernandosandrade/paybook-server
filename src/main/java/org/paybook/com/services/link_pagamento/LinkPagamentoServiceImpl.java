package org.paybook.com.services.link_pagamento;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.exception.EntityType;
import org.paybook.com.exception.ExceptionFactory;
import org.paybook.com.exception.ExceptionType;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JBossLog
@ApplicationScoped
public class LinkPagamentoServiceImpl implements LinkPagamentoService {

    @Inject
    LinkPagamentoRepository linkPagamentoRepository;

    @Override
    public LinkPagamentoModel add(LinkPagamentoModel linkPagamento) {
        Optional<LinkPagamentoModel> model = this.linkPagamentoRepository.getById(linkPagamento.id());
        if (model.isPresent()) {
            throw exception(EntityType.LINK_PAGAMENTO, ExceptionType.DUPLICATE_ENTITY, linkPagamento.id());
        }
        return this.linkPagamentoRepository.add(linkPagamento);
    }

    @Override
    public LinkPagamentoModel update(LinkPagamentoModel linkPagamento) {
        return null;
    }

    @Override
    public void delete(LinkPagamentoModel linkPagamento) {
    }

    @Override
    public Optional<LinkPagamentoModel> getById(String idLinkPagamento) {
        return Optional.empty();
    }

    @Override
    public List<LinkPagamentoModel> getAllByCobranca(String idCobranca) {
        return Collections.emptyList();
    }

    @Override
    public void payLink(LinkPagamentoModel linkPagamento) {

    }

    @Override
    public void setLinkAsPaid(LinkPagamentoModel linkPagamento) {

    }

    @Override
    public void cancelLink(LinkPagamentoModel linkPagamento) {

    }

    @Override
    public void setLinkAsCanceled(LinkPagamentoModel linkPagamento) {

    }

    @Override
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
