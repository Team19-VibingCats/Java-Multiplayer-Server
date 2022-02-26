package eckigames.resource;

import com.google.gson.Gson;
import eckigames.dto.PlayerDTO;
import eckigames.dto.WorldDTO;
import eckigames.service.RequestManagerService;
import org.json.simple.JSONArray;

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
    public String onMessage(String requestString, Session session) {
        String[] requestData = requestString.split("&");
        String token = requestData[1];
        String worldName = requestData[2];
        PlayerDTO player = requestManagerService.verifyUser(token, worldName);
        player.setSession(session);
        player.setLastRequest((int) System.currentTimeMillis());

        WorldDTO world = requestManagerService.getWorld(worldName);

        String[] requestList = requestData[0].split("#");
        for(String requestText : requestList) {
            if(requestText.length() <= 3) continue;
            String[] request = requestText.split("%");
            world.addUnprocessedEvent(player, request[1], request[0]);
        }

        Object[] unprocessedRequests = world.popUnprocessedEvents(player);
        JSONArray jsArray = new JSONArray();
        for (int i = 0; i < unprocessedRequests.length; i++) {
            jsArray.add(unprocessedRequests[i]);
        }

        return jsArray.toJSONString();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        requestManagerService.removePlayer(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}