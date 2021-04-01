package org.paybook.com.controller.v1;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.ExtendedResponseStatus;
import org.paybook.com.dto.Cobranca111Dto;
import org.paybook.com.services.cobranca.Cobranca111Service;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * API REST para cobrancas do tipo 111.
 */
@JBossLog
@Path("/v1/cobranca/101/111")
public class Cobranca111 {

    @Inject
    Cobranca111Service cobrancaService;

    /**
     * Registra a cobranca.
     *
     * @param idCobranca
     * @return
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registro(@PathParam("id") String idCobranca) {
        Optional<Cobranca111Model> cobranca111Model = this.cobrancaService.obter(idCobranca);
        if (cobranca111Model.isEmpty()) {
            // cobranca nao encontrada
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        var cobranca111ModelRegistrado = this.cobrancaService.registrar(cobranca111Model.get());
        return Response.ok(cobranca111ModelRegistrado)
                .status(Response.Status.ACCEPTED)
                .build();

    }

    /**
     * @param idCobranca
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obter(@PathParam("id") String idCobranca) {
        Optional<Cobranca111Model> cobrancaModel = this.cobrancaService.obter(idCobranca);
        if (cobrancaModel.isEmpty()) {
            // cobranca nao encontrada
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(cobrancaModel)
                .status(Response.Status.OK)
                .build();
    }

    /**
     * @param idCobranca
     * @return
     */
    @GET
    @Path("/reactive/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> obterReactive(@PathParam("id") String idCobranca) {
        return this.cobrancaService.obterReactive(idCobranca)
                .onItem().transform(optCobranca ->
                        optCobranca.isPresent() ? Response.ok(optCobranca.get()) :
                                Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    /**
     * Inclui uma nova cobrança, registrando ela em seguida.
     *
     * @param cobranca
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response incluir(Cobranca111Dto cobranca) {
        Cobranca111Model novaCobranca = this.cobrancaService.novaCobranca(cobranca.getIdBook(),
                cobranca.getValor(),
                cobranca.getDataVencimento(),
                cobranca.getDestinatario());
        return Response.ok(novaCobranca)
                .status(Response.Status.CREATED)
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterarStatus(@PathParam("id") String idCobranca, Cobranca111Dto cobranca) {
        Optional<Cobranca111Model> cobranca111Model = this.cobrancaService.obter(idCobranca);
        if (cobranca111Model.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        if (cobranca.getStatus() == null) {
            return Response.status(ExtendedResponseStatus.UNPROCESSABLE_ENTITY)
                    .entity("required attributes: status=[CHARGE_PAID, CHARGE_CANCELED], received status=[null]")
                    .build();
        }
        Cobranca111Model cobrancaModel = cobranca111Model.get();

        switch (cobranca.getStatus()) {
            case CHARGE_PAID:
                this.cobrancaService.pagar(cobrancaModel);
                break;
            case CHARGE_CANCELED:
                this.cobrancaService.cancelar(cobrancaModel);
                break;
            case CHARGE_OPEN:
                return Response.status(ExtendedResponseStatus.UNPROCESSABLE_ENTITY)
                        .entity("required attributes: status=[CHARGE_PAID, CHARGE_CANCELED], received status=[CHARGE_OPEN]")
                        .build();
            case WAITING_PAYMENT:
                return Response.status(ExtendedResponseStatus.UNPROCESSABLE_ENTITY)
                        .entity("required attributes: status=[CHARGE_PAID, CHARGE_CANCELED], received status=[WAITING_PAYMENT]")
                        .build();
        }
        return Response.ok(cobranca)
                .status(Response.Status.OK)
                .build();
    }

}
