package eckigames.resource.mapper;

import eckigames.service.exception.TooManyPlayersException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TooManyPlayersExceptionMapper implements ExceptionMapper<TooManyPlayersException> {
    @Override
    public Response toResponse(TooManyPlayersException e) {
        return Response.status(403).entity("The server is full.").build();
    }
}
