package org.paybook.com.controller.v1;

import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.services.cobranca.Cobranca111Service;
import org.paybook.com.services.cobranca.dao.Cobranca111Model;
import org.paybook.com.services.cobranca.dao.CobrancaRepository;

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
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public class Cobranca111 {

    @Inject
    //FirebaseCobrancaRepository cobrancaRepository;
    CobrancaRepository cobrancaRepository;

    @Inject
    Cobranca111Service cobrancaService;

    /**
     * Registra a cobranca.
     *
     * @param idCobranca
     * @return
     */
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registro(@PathParam("id") String idCobranca) {
        Optional<Cobranca111Model> cobranca111Model = this.cobrancaService.obter(idCobranca);
        if (cobranca111Model.isEmpty()) {
            // cobranca nao encontrada
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } else {
            var cobranca111ModelRegistrado = this.cobrancaService.registrar(cobranca111Model.get());
            return Response.ok(cobranca111ModelRegistrado)
                    .status(Response.Status.ACCEPTED)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obter(@PathParam("id") String idCobranca) {
        Optional<Cobranca111Model> cobrancaModel = this.cobrancaService.obter(idCobranca);
        if (cobrancaModel.isEmpty()) {
            // cobranca nao encontrada
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } else {
            return Response.ok(cobrancaModel)
                    .status(Response.Status.OK)
                    .build();
        }
    }
}
