package eckigames.dto;

import java.util.*;

public class WorldDTO {
    private HashMap<String, PlayerDTO> tokens = new HashMap<>();
    int serverTime;
    String name;

    private HashMap<PlayerDTO, List<String>> unprocessedRequests = new HashMap<>();
    private List<String> persistentRequests = new ArrayList<>();

    private PlayerDTO host;

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

        if (!unprocessedRequests.containsKey(playerDTO)) {
            unprocessedRequests.put(playerDTO,new ArrayList<>());
        }

        return new LoginInformationDTO(token,serverTime);
    }

    public void addPlayerToGame(PlayerDTO playerDTO) {
        playerDTO.setInGame(true);
        if(host == null) switchHost();

        List<String> requestList = unprocessedRequests.get(playerDTO);
        for(String persistentRequest : persistentRequests) {
            requestList.add(persistentRequest);
        }

        if(!playerDTO.equals(host)) {
            String hostRequest = "5:\"isClient\",3:\"/root/TokenHandler\", 0:2}";
            requestList.add(hostRequest);
        }
    }

    public void switchHost() {
        host = null;

        for(PlayerDTO player : tokens.values()) {
            if(player.isInGame()) {
                host = player;
                hostChanged();
                return;
            }
        }
    }

    public void addUnprocessedEvent(PlayerDTO from, String unprocessedRequest, String persistent) {
        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            PlayerDTO player = entry.getValue();

            if (player.isInGame() && player != from) {
                if (!unprocessedRequests.containsKey(player)) {
                    unprocessedRequests.put(player,new ArrayList<>());
                }
                unprocessedRequests.get(player).add(unprocessedRequest);
            }
        }

        boolean bool = Boolean.parseBoolean(persistent.toLowerCase());
        if (bool) {
            persistentRequests.add(unprocessedRequest);
        }
    }

    public Object[] popUnprocessedEvents(PlayerDTO player) {
        List<String> requests = unprocessedRequests.get(player);
        Object[] array = requests.toArray();
        unprocessedRequests.get(player).clear();
        return array;
    }

    private void hostChanged() {
        System.out.println("host changed");
        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            PlayerDTO player = entry.getValue();
            if (!unprocessedRequests.containsKey(player)) {
                unprocessedRequests.put(player,new ArrayList<>());
            }

            if (player.equals(host)) {
                String hostRequest = "5:\"isHost\",3:\"/root/TokenHandler\", 0:2}";
                unprocessedRequests.get(player).add(hostRequest);
            } else {
                String hostRequest = "5:\"isClient\",3:\"/root/TokenHandler\", 0:2}";
                unprocessedRequests.get(player).add(hostRequest);
            }
        }
    }

    public void removeOldPlayers() {
        List<String> tokensToRemove = new ArrayList<>();

        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            PlayerDTO player = entry.getValue();
            if(player.isOld()) tokensToRemove.add(entry.getKey());
        }

        boolean hostRemoved = false;
        for (String token: tokensToRemove) {
            if(tokens.get(token).equals(host)) hostRemoved = true;

            String request = "5:\"removeFakeObject\",3:\"/root/FakeObjectHandler\",9:\"/root/CurrentScene/"+name+"\",0:2}";
            persistentRequests.add(request);

            tokens.remove(token);
            for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
                PlayerDTO player = entry.getValue();
                if (!unprocessedRequests.containsKey(player)) {
                    unprocessedRequests.put(player, new ArrayList<>());
                }

                unprocessedRequests.get(player).add(request);
            }
        }

        if(hostRemoved) switchHost();
    }

    public void removePlayer(PlayerDTO playerDTO) {
        String name = "";
        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            String token = entry.getKey();
            PlayerDTO player = entry.getValue();
            name = player.getName();

            if(player.equals(playerDTO)) {
                tokens.remove(token);
                if (playerDTO.equals(host)) switchHost();
                break;
            }
        }

        String request = "5:\"removeFakeObject\",3:\"/root/FakeObjectHandler\",9:\"/root/CurrentScene/"+name+"\",0:2}";
        persistentRequests.add(request);
        for(Map.Entry<String, PlayerDTO> entry : tokens.entrySet()) {
            PlayerDTO player = entry.getValue();
            if (!unprocessedRequests.containsKey(player)) {
                unprocessedRequests.put(player, new ArrayList<>());
            }

            unprocessedRequests.get(player).add(request);
        }
    }

    public PlayerDTO getHost() {
        return host;
    }
}
