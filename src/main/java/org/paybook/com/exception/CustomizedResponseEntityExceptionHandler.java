package org.paybook.com.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@ApplicationScoped
public class CustomizedResponseEntityExceptionHandler implements ExceptionMapper<ExceptionFactory.ApplicationException> {

    @Override
    public Response toResponse(ExceptionFactory.ApplicationException exception) {
        if (exception instanceof ExceptionFactory.EntityNotFoundException) {
            return handleNotFountExceptions(exception);
        } else if (exception instanceof ExceptionFactory.DuplicateEntityException) {
            return handleDuplicateEntityException(exception);
        }
        return Response.serverError().build();
    }

    public final Response handleNotFountExceptions(Exception ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
    }

    public final Response handleDuplicateEntityException(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
    }

}
