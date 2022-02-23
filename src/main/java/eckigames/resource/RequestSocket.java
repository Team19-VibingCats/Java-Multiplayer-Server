package eckigames.resource;

import com.google.gson.Gson;
import eckigames.dto.PlayerDTO;
import eckigames.dto.WorldDTO;
import eckigames.service.RequestManagerService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/socket")
public class RequestSocket {
    RequestManagerService requestManagerService = new RequestManagerService();
    Gson g = new Gson();

    @OnOpen
    public void onOpen() {
        System.out.println("Open Connection ...");
    }

    @OnMessage
    public String onMessage(String requestString) {
        String[] requestData = requestString.split("/");
        String request = requestData[0];
        String token = requestData[1];
        String worldName = requestData[2];
        PlayerDTO player = requestManagerService.verifyUser(token, worldName);
        player.setLastRequest((int) System.currentTimeMillis());

        WorldDTO world = requestManagerService.getWorld(worldName);

        return g.toJson(world.popUnprocessedEvents(player));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}