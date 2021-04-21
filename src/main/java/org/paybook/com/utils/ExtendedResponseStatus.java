package org.paybook.com.utils;

import javax.ws.rs.core.Response;

/**
 * Classe com implementacao de htpp status type oficiais mas nao implementados pelo JAX-RS.
 */
public enum ExtendedResponseStatus implements Response.StatusType {

    /**
     * 422 Unprocessable Entity, see <a href="https://tools.ietf.org/html/rfc4918#section-11.2">HTTP Extensions
     * WebDAV</a>.
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", Response.Status.Family.CLIENT_ERROR);

    private final int code;
    private final String reason;
    private final Response.Status.Family family;

    ExtendedResponseStatus(int code, String reason, Response.Status.Family family) {
        this.code = code;
        this.reason = reason;
        this.family = family;
    }

    @Override
    public int getStatusCode() {
        return this.code;
    }

    @Override
    public Response.Status.Family getFamily() {
        return this.family;
    }

    @Override
    public String getReasonPhrase() {
        return toString();
    }

    @Override
    public String toString() {
        return this.reason;
    }
}
