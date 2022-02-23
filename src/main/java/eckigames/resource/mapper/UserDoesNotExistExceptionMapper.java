package eckigames.resource.mapper;

import eckigames.service.exception.UserDoesNotExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserDoesNotExistExceptionMapper implements ExceptionMapper<UserDoesNotExistException> {
    @Override
    public Response toResponse(UserDoesNotExistException e) {
        return Response.status(401).entity("User does not exist").build();
    }
}
