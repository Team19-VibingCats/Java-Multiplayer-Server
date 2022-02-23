package eckigames.dto;

import java.util.HashMap;

public class RemovedObject {
    String gameType = "object";
    String action = "remove";
    public int id = 0;
    boolean removedByPlayer = false;
    public String destroyedByPlayer;

    private HashMap<String, Boolean> players = new HashMap<>();

    public void setPlayer(String token, boolean recieved) {
        players.put(token, recieved);
    }

    public void updatePlayer(String token, boolean recieved) {
        players.replace(token, recieved);
    }

    public boolean hasRecieved(String token) {
        if (!players.get(token)) {
            players.replace(token, true);
            return false;
        }
        return true;
    }
}
