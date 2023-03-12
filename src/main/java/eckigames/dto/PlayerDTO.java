package eckigames.dto;

import javax.websocket.Session;
import java.util.HashMap;

public class PlayerDTO {
    String name;

    int lastRequest;

    private Session session;

    boolean inGame = false;

    HashMap<String, String> playerData = new HashMap<String, String>();

    public void setPlayerData(String key, String data) {
        playerData.put(key, data);
    }

    public String getPlayerData(String key) {
        return playerData.get(key);
    }

    public PlayerDTO() {

    }

    PlayerDTO(String name) {
        this.name = name;
        lastRequest = (int) System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(int lastRequest) {
        this.lastRequest = lastRequest;
    }

    public boolean isOld() {
        if(((int) System.currentTimeMillis() - getLastRequest()) / 1000 >= 30) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getJSON() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.putAll(playerData);
        data.put("name", name);

        StringBuilder sb = new StringBuilder("{");
        for (String key : data.keySet()) {
            sb.append("\"" + key + "\":\"" + data.get(key) + "\",");
        }
        sb.deleteCharAt(sb.length() - 1);  // Remove the extra comma
        sb.append("}");

        String json = sb.toString();
        return json;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        PlayerDTO player = (PlayerDTO) o;
        // field comparison
        return name.equals(player.getName());
    }
}
