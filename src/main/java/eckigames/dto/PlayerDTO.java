package eckigames.dto;

import javax.websocket.Session;

public class PlayerDTO {
    String name;

    int lastRequest;

    private Session session;

    boolean inGame = false;

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
        if(((int) System.currentTimeMillis() - getLastRequest()) / 1000 >= 120) {
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
        return "{\"name\" : \"" + name
                + "\"}";
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
