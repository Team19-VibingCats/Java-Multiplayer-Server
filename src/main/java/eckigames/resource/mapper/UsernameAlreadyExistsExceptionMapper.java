package eckigames.resource.mapper;

import eckigames.service.exception.UsernameAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernameAlreadyExistsExceptionMapper implements ExceptionMapper<UsernameAlreadyExistsException> {
    @Override
    public Response toResponse(UsernameAlreadyExistsException e) {
        return Response.status(403).entity("Username already exists.").build();
    }
}