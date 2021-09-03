package org.paybook.com.controller.v1;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.paybook.com.controller.dto.ChargeDto;
import org.paybook.com.exception.EntityType;
import org.paybook.com.exception.ExceptionFactory;
import org.paybook.com.exception.ExceptionType;
import org.paybook.com.services.ChargesCollection;
import org.paybook.com.services.cobranca.Charge111Service;
import org.paybook.com.services.cobranca.EnumChargeStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API REST for charges 111.
 * <p>
 * Below an API overview
 * </p>
 * <pre>
 * GET  /users/{user}/books/{book}/charges                      => return all charges of informed book
 * GET  /users/{user}/books/{book}/charges/111/{charge}             => return specific charge of informed book
 * POST /users/{user}/books/{book}/charges/111/{charge}/paylinks    => create payment links for this charge
 * PUT  /users/{user}/books/{book}/charges/111/{charge}/paylinks    => update payment links for this charge
 * </pre>
 */
@JBossLog
@Path("/v1/users/{user}/books/{book}/charges/111/{charge}")
public class Charge111Api {

    private final Charge111Service chargeService;

    public Charge111Api(Charge111Service chargeService) {
        this.chargeService = chargeService;
    }

    /**
     * Return all charges for the specific {@code book_id}.
     * <pre>
     * 200 - OK. return list of charges
     * 404 - {@code book_id} not found
     * </pre>
     */
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Uni<Response> findAllCharges(@PathParam("book_id") String idBook) {
//        return Uni.createFrom().nullItem();
//    }

    /**
     * Return the specific charge from the book
     * <pre>
     * 200 - return the requested charge
     * 404 - charge not found
     * </pre>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> findCharge(@PathParam("user") String user, @PathParam("book") String book,
                                    @PathParam("charge") String charge) {
        ChargesCollection chargesCollection = ChargesCollection.of(user, book);
        log.infof("finding charge=[%s] on chargesCollection=[%s]", charge,
                chargesCollection.getPath());

        return this.chargeService.find(charge, chargesCollection)
                .onItem()
                .transform(optCobranca -> optCobranca.isPresent() ? optCobranca.get() : null)
                .onItem()
                .ifNotNull()
                .transform((retrivedCharge) -> Response.status(
                                Response.Status.OK)
                        .entity(retrivedCharge)
                        .build())
                .onItem()
                .ifNull()
                .failWith(() -> {
                    log.infof("no charge found for charge=[%s] on chargesCollection=[%s]", charge,
                            chargesCollection.getPath());
                    return ExceptionFactory.throwException(EntityType.COBRANCA,
                            ExceptionType.ENTITY_NOT_FOUND,
                            charge);
                });
    }

    /**
     * Create the payment link for this charge
     * <pre>
     * 201 - link created
     * 404 - charge not found
     * </pre>
     */
    @POST
    @Path("/paylinks")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createPaymentLink(@PathParam("user") String user, @PathParam("book") String book,
                                           @PathParam("charge") String charge) {
        ChargesCollection chargesCollection = ChargesCollection.of(user,
                book);
        log.infof("trying to create payment links for charge=[%s] on chargesCollection=[%s]", charge,
                chargesCollection.getPath());

        return this.chargeService.find(charge, chargesCollection)
                .onItem()
                .transform(optCobranca ->
                        optCobranca.isPresent() ?
                                optCobranca.get() :
                                null)
                .onItem()
                .ifNotNull()
                .transformToUni(cobranca -> {
                            if (!EnumChargeStatus.CHARGE_OPEN.equals(cobranca.status()))
                                throw ExceptionFactory.throwException(EntityType.COBRANCA,
                                        ExceptionType.ILLEGAL_ARGUMENT,
                                        "status", EnumChargeStatus.CHARGE_OPEN.name(),
                                        cobranca.status().name());
                            if (!cobranca.paymentLinks().isEmpty())
                                throw ExceptionFactory.throwExceptionWithId(EntityType.COBRANCA,
                                        ExceptionType.DUPLICATE_ENTITY,
                                        "links_already_generated", cobranca.documentID());

                            return this.chargeService.generatePaymentLinks(cobranca, chargesCollection)
                                    .onItem()
                                    .transform((cobrancaWithLink) -> Response.status(
                                                    Response.Status.CREATED)
                                            .entity(cobrancaWithLink)
                                            .build());
                        }
                )
                .onItem()
                .ifNull()
                .failWith(() -> {
                    log.infof("no charge found for charge=[%s] on chargesCollection=[%s]", charge,
                            chargesCollection.getPath());
                    return ExceptionFactory.throwException(EntityType.COBRANCA,
                            ExceptionType.ENTITY_NOT_FOUND,
                            charge);
                });
    }

    /**
     * Update the payment link for this charge
     * <pre>
     * 201 - link created
     * 404 - charge not found
     * </pre>
     */
    @PUT
    @Path("/paylinks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updatePaymentLink(ChargeDto chargeDto) {
        return Uni.createFrom().nullItem();
    }
}
