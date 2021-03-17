package org.paybook.com.services.link_pagamento;

import org.paybook.com.services.link_pagamento.dao.LinkPagamentoModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipulacao de links de pagamento. Fornece as operacoes disponíveis para um link, validando regras de
 * negocio.
 */
@ApplicationScoped
public interface LinkPagamentoService {

    LinkPagamentoModel add(LinkPagamentoModel linkPagamento);

    LinkPagamentoModel update(LinkPagamentoModel linkPagamento);

    void delete(LinkPagamentoModel linkPagamento);

    Optional<LinkPagamentoModel> getById(String idLinkPagamento);

    List<LinkPagamentoModel> getAllByCobranca(String idCobranca);

    void changeToPaid(LinkPagamentoModel linkPagamento);

    void changeToCancelled(LinkPagamentoModel linkPagamento);
}
