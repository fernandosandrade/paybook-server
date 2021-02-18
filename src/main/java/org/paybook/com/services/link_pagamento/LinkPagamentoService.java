package org.paybook.com.services.link_pagamento;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipulacao de links de pagamento. Fornece as operacoes disponíveis para um link, validando regras de
 * negocio.
 */
@ApplicationScoped
public interface LinkPagamentoService {

    /**
     * Cadastra o link de pagamento.
     *
     * @param link
     * @return
     */
    LinkPagamento add(LinkPagamento link);

    LinkPagamento update(LinkPagamento link);

    void delete(LinkPagamento link);

    Optional<LinkPagamento> getById(String linkPagamentoID);

    List<LinkPagamento> getAllByCobranca(String cobrancaID);

    void changeToPaid(LinkPagamento link);

    void changeToCancelled(LinkPagamento link);
}
