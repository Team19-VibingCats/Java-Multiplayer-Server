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

    public boolean isOld() {
        if(((int) System.currentTimeMillis() - getLastRequest()) / 1000 >= 10) {
            return true;
        } else {
            return false;
        }
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
