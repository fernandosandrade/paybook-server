package org.paybook.com.controller.v1;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.dto.LinkPagamentoDto;
import org.paybook.com.services.cobranca.EnumStatusCobranca;
import org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento;
import org.paybook.com.services.link_pagamento.LinkPagamentoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@JBossLog
@Path("/v1/linkpagamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LinkPagamento {

    @Inject
    LinkPagamentoService linkPagamentoService;

    /**
     * Retorna o link de pagamento referente ao id do link informado.
     *
     * @param linkPagamentoDto link de pagamento com o id desejado
     * @return
     */
    @GET
    public Response obter(LinkPagamentoDto linkPagamentoDto) {
        return null;
    }

    /**
     * Permite pagar ou cancelar o link de pagamento. Apos a alteracao do estado, a cobranca ao qual o link pertence é
     * comunicada para processar a alteração.
     * <p>
     * Apenas links em {@link org.paybook.com.services.link_pagamento.EnumStatusLinkPagamento#WAITING_PAIMENT} podem ter
     * seu estado alterado.
     * </p>
     * <p>
     * Um link pode ter seu estado alterado para os seguintes valores:
     *     <ul>
     *         <li>{@linkplain EnumStatusLinkPagamento#LINK_PAID}</li>
     *         <li>{@linkplain EnumStatusLinkPagamento#LINK_SET_AS_PAID}</li>
     *         <li>{@linkplain EnumStatusLinkPagamento#LINK_CANCELLED}</li>
     *         <li>{@linkplain EnumStatusLinkPagamento#LINK_EXPIRED}</li>
     *     </ul>
     * </p>
     *
     * @return
     */
    @PATCH
    public Response alterarStatus() {
        return null;
    }

    /**
     * Inclui um link de pagamento para a cobranca informada.
     * <p>
     * Para incluir um novo link de pagamento a uma cobranca, a mesma deve estar em {@link
     * EnumStatusCobranca#CHARGE_OPEN} ou {@link EnumStatusCobranca#WAITING_PAYMENT}. A soma dos links de pagamento para
     * uma cobranca nao podem ser superiores ao valor total da cobranca, mas podem ser inferiores. O prazo máximo de
     * expiracao do link de pagamento é o prazo maximo da cobrança.
     * </p>
     *
     * @return link de pagamento criado
     */
    @POST
    public Response criar(LinkPagamentoDto linkPagamento) {
        return Response.ok(linkPagamento).build();
    }

    @DELETE
    public Response deletar(LinkPagamentoDto linkPagamento) {
        return Response.ok(linkPagamento).build();
    }


}
