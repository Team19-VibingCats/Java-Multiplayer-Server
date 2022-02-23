package eckigames.resource;

import eckigames.dto.WorldDTO;
import eckigames.service.RequestManagerService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/world")
public class WorldResource {
    RequestManagerService requestManagerService = new RequestManagerService();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorld(WorldDTO worldDTO) {
        requestManagerService.createWorld(worldDTO);
        return Response.status(Response.Status.OK).entity("succes").build();
    }
}
