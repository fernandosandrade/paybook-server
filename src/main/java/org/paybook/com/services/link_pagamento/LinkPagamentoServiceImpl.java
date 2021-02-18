package org.paybook.com.services.link_pagamento;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.exception.EntityType;
import org.paybook.com.exception.ExceptionFactory;
import org.paybook.com.exception.ExceptionType;
import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel2;
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
    public LinkPagamento add(LinkPagamento link) {
        Optional<LinkPagamentoModel2> model = this.linkPagamentoRepository.getById(link.getId());
        if (model.isPresent()) {
            throw exception(EntityType.LINK_PAGAMENTO, ExceptionType.DUPLICATE_ENTITY, link.getId());
        }
        this.linkPagamentoRepository.save(link.getModel());
        return link;
    }

    @Override
    public LinkPagamento update(LinkPagamento link) {
        return null;
    }

    @Override
    public void delete(LinkPagamento link) {

    }

    @Override
    public Optional<LinkPagamento> getById(String linkPagamentoID) {
        return Optional.empty();
    }

    @Override
    public List<LinkPagamento> getAllByCobranca(String cobrancaID) {
        return Collections.emptyList();
    }

    @Override
    public void changeToPaid(LinkPagamento link) {

    }

    @Override
    public void changeToCancelled(LinkPagamento link) {

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
