package eckigames.resource.mapper;

import eckigames.service.exception.TooManyActiveWorldsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TooManyActiveWorldsExceptionMapper implements ExceptionMapper<TooManyActiveWorldsException> {
    @Override
    public Response toResponse(TooManyActiveWorldsException e) {
        return Response.status(403).entity("There are too many world open.").build();
    }
}
