package eckigames.dto;

public class LoginInformationDTO {
    String token;
    int serverTime;

    public LoginInformationDTO(String token, int serverTime) {
        this.token = token;
        this.serverTime = serverTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }
}
