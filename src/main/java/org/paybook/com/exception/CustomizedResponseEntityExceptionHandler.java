package org.paybook.com.exception;

import org.paybook.com.ExtendedResponseStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomizedResponseEntityExceptionHandler implements ExceptionMapper<ExceptionFactory.ApplicationException> {

    @Override
    public Response toResponse(ExceptionFactory.ApplicationException exception) {
        if (exception instanceof ExceptionFactory.EntityNotFoundException) {
            return handleNotFountExceptions(exception);
        } else if (exception instanceof ExceptionFactory.DuplicateEntityException) {
            return handleDuplicateEntityException(exception);
        } else if (exception instanceof ExceptionFactory.IllegalArgumentException) {
            return handleIllegalArgumentException(exception);
        }
        return Response.serverError().build();
    }

    public final Response handleNotFountExceptions(Exception ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
    }

    public final Response handleDuplicateEntityException(Exception ex) {
        return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
    }

    public final Response handleIllegalArgumentException(Exception ex) {
        return Response.status(ExtendedResponseStatus.UNPROCESSABLE_ENTITY).entity(ex.getMessage()).build();
    }

}
