package eckigames.resource;

import eckigames.dto.LoginInformationDTO;
import eckigames.dto.PlayerDTO;
import eckigames.service.RequestManagerService;
import org.json.simple.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/player")
public class PlayerResource {
    RequestManagerService requestManagerService = new RequestManagerService();

    @POST
    @Path("/login/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginPlayer(PlayerDTO playerDTO, @PathParam("worldName") String worldName) {
        LoginInformationDTO token = requestManagerService.loginUser(playerDTO, worldName);
        return Response.status(Response.Status.OK).entity(token).build();
    }

    @POST
    @Path("/{token}/enterWorld/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enterWorld(@PathParam("worldName") String worldName, @PathParam("token") String token) {
        PlayerDTO playerDTO = requestManagerService.verifyUser(token,worldName);
        requestManagerService.getWorld(worldName).addPlayerToGame(playerDTO);

        return Response.status(Response.Status.OK).entity("ok").build();
    }

    @GET
    @Path("/{token}/lobby/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLobby(PlayerDTO playerDTO, @PathParam("worldName") String worldName, @PathParam("token") String token) {
        requestManagerService.verifyUser(token,worldName);
        List<PlayerDTO> players = requestManagerService.getAllPlayers(worldName);

        JSONArray jsArray = new JSONArray();
        for (int i = 0; i < players.size(); i++) {
            jsArray.add(players.get(i).getJSON());
        }

        return Response.status(Response.Status.OK).entity(jsArray.toJSONString()).build();
    }

    @DELETE
    @Path("/{token}/logout/{worldName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logoutPlayer(PlayerDTO playerDTO, @PathParam("worldName") String worldName, @PathParam("token") String token) {
        PlayerDTO player = requestManagerService.verifyUser(token,worldName);
        requestManagerService.getWorld(worldName).removePlayer(player);
        return Response.status(Response.Status.OK).entity("ok").build();
    }
}