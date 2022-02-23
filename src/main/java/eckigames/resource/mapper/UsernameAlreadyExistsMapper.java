package eckigames.resource.mapper;

import eckigames.service.exception.UsernameAlreadyExists;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernameAlreadyExistsMapper implements ExceptionMapper<UsernameAlreadyExists> {
    @Override
    public Response toResponse(UsernameAlreadyExists e) {
        return Response.status(403).entity("Username already exists.").build();
    }
}