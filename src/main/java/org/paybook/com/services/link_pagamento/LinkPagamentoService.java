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

    /**
     * Retorna todos os link de pagamento associados a uma cobrança.
     *
     * @param idCobranca id da cobranca pai dos links de pagamento
     * @return lista contendo os links de pagamento
     */
    List<LinkPagamentoModel> getAllByCobranca(String idCobranca);

    /**
     * Registra o pagamento da cobranca.
     *
     * @param linkPagamento
     */
    void payLink(LinkPagamentoModel linkPagamento);

    /**
     * Seta o link como pago de forma manual.
     *
     * @param linkPagamento
     */
    void setLinkAsPaid(LinkPagamentoModel linkPagamento);

    /**
     * Cancela o link.
     * <p>
     * Deve ser utilizado quando o cancelamento se da de forma sistema e automatica, como por exemplo, o cancelamento da
     * cobranca que gerou o link.
     * </p>
     *
     * @param linkPagamento
     */
    void cancelLink(LinkPagamentoModel linkPagamento);

    /**
     * Cancela o link, mas com a origem do evento sendo
     *
     * @param linkPagamento
     */
    void setLinkAsCanceled(LinkPagamentoModel linkPagamento);

    void expireLink(LinkPagamentoModel linkPagamento);
}
