package eckigames.resource;

import com.google.gson.Gson;
import eckigames.dto.LoginInformationDTO;
import eckigames.dto.PlayerDTO;
import eckigames.service.RequestManagerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/player")
public class PlayerResource {
    RequestManagerService requestManagerService = new RequestManagerService();
    Gson g = new Gson();

    @POST
    @Path("/login/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginPlayer(PlayerDTO playerDTO, @PathParam("worldName") String worldName) {
        LoginInformationDTO token = requestManagerService.loginUser(playerDTO, worldName);
        return Response.status(Response.Status.OK).entity(token).build();
    }

    @GET
    @Path("/{token}/lobby/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLobby(PlayerDTO playerDTO, @PathParam("worldName") String worldName, @PathParam("token") String token) {
        requestManagerService.verifyUser(token,worldName);
        List<PlayerDTO> players = requestManagerService.getAllPlayers(worldName);
        return Response.status(Response.Status.OK).entity(g.toJson(players)).build();
    }
}