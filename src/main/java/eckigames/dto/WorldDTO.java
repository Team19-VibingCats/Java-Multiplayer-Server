package eckigames.dto;

import java.util.*;

public class WorldDTO {
    private HashMap<String, PlayerDTO> tokens = new HashMap<>();
    int serverTime;
    String name;

    private HashMap<PlayerDTO, List<String>> unprocessedRequests = new HashMap<>();

    public WorldDTO(int serverTime, String name) {
        this.serverTime = serverTime;
        this.name = name;
    }

    WorldDTO() {
        tokens = new HashMap<String, PlayerDTO>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public List<PlayerDTO> getAllPlayers() {
        return new ArrayList<PlayerDTO>(tokens.values());
    }

    public PlayerDTO getPlayerByToken(String token) {
        return tokens.get(token);
    }

    public LoginInformationDTO loginPlayer(PlayerDTO playerDTO) {
        String token = UUID.randomUUID().toString();
        tokens.put(token,playerDTO);
        return new LoginInformationDTO(token,serverTime);
    }

    public void addUnprocessedEvent(PlayerDTO from, String unprocessedRequest) {
        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            PlayerDTO player = entry.getValue();

            if (player != from) {
                if (!unprocessedRequests.containsKey(player)) {
                    unprocessedRequests.put(player,new ArrayList<>());
                }
                unprocessedRequests.get(player).add(unprocessedRequest);
            }
        }
    }

    public Object[] popUnprocessedEvents(PlayerDTO player) {
        List<String> requests = unprocessedRequests.get(player);
        unprocessedRequests.get(player).clear();
        return requests.toArray();
    }
}
