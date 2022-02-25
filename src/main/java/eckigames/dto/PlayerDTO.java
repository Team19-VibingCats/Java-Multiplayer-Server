package eckigames.dto;

public class PlayerDTO {
    String name;

    int lastRequest;

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

    public boolean isOld(WorldDTO world) {
        if(((int) System.currentTimeMillis() - getLastRequest()) / 1000 >= 10) {
            if(world.getHost() == this) world.switchHost();
            return true;
        } else {
            return false;
        }
    }
}
