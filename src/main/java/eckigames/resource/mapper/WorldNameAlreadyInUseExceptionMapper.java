package eckigames.resource.mapper;

import eckigames.service.exception.WorldNameAlreadyInUseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WorldNameAlreadyInUseExceptionMapper implements ExceptionMapper<WorldNameAlreadyInUseException> {
    @Override
    public Response toResponse(WorldNameAlreadyInUseException e) {
        return Response.status(403).entity("Worldname already exists.").build();
    }
}