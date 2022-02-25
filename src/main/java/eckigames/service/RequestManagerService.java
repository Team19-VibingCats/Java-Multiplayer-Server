package eckigames.service;

import eckigames.dto.LoginInformationDTO;
import eckigames.dto.PlayerDTO;
import eckigames.dto.WorldDTO;
import eckigames.service.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestManagerService {
    static HashMap<String, WorldDTO> activeWorlds;

    static {
        activeWorlds = new HashMap<String, WorldDTO>();
    }

    public void createWorld(WorldDTO worldDTO) {
        if(activeWorlds.size() >= 100) {
            throw new TooManyActiveWorldsException();
        }

        if(!activeWorlds.containsKey(worldDTO.getName())) {
            activeWorlds.put(worldDTO.getName(),worldDTO);
        } else {
            throw new WorldNameAlreadyInUseException();
        }
    }

    public LoginInformationDTO loginUser(PlayerDTO playerDTO, String worldName) {
        WorldDTO worldDTO = getWorld(worldName);
        if (worldDTO != null) worldDTO.removeOldPlayers();

        List<PlayerDTO> activePlayers = activeWorlds.get(worldName).getAllPlayers();
        if(activePlayers.size() <= 150) {
            PlayerDTO player = getPlayer(playerDTO, worldName);
            if(player == null) {
                playerDTO.setLastRequest((int) System.currentTimeMillis());
                return activeWorlds.get(worldName).loginPlayer(playerDTO);
            } else {
                throw new UsernameAlreadyExistsException();
            }
        } else {
            throw new TooManyPlayersException();
        }
    }

    public PlayerDTO verifyUser(String token, String worldName) {
        WorldDTO worldDTO = getWorld(worldName);
        if (worldDTO != null) worldDTO.removeOldPlayers();

        PlayerDTO player = getPlayerByToken(token, worldName);
        if(player == null) {
            throw new UserDoesNotExistException();
        } else {
            player.setLastRequest((int) System.currentTimeMillis());
        }
        return player;
    }

    public List<PlayerDTO> getPlayers(String token, String worldName) {
        List<PlayerDTO> players = new ArrayList<>(activeWorlds.get(worldName).getAllPlayers());
        players.remove(getPlayerByToken(token,worldName));
        return players;
    }

    public List<PlayerDTO> getAllPlayers(String worldName) {
        List<PlayerDTO> players = new ArrayList<>(activeWorlds.get(worldName).getAllPlayers());
        return players;
    }

    public PlayerDTO getPlayerByToken(String token, String worldName) {
        System.out.println(worldName);
        System.out.println(activeWorlds.keySet().toString());
        System.out.println(activeWorlds.containsKey(worldName));
        return activeWorlds.get(worldName).getPlayerByToken(token);
    }

    public PlayerDTO getPlayer(PlayerDTO playerDTO, String worldName) {
        for(PlayerDTO playerDTO1:activeWorlds.get(worldName).getAllPlayers()) {
            if(playerDTO.getName().equals(playerDTO1.getName())) {
                return playerDTO1;
            }
        }
        return null;
    }

    public WorldDTO getWorld(String worldName) {
        return activeWorlds.get(worldName);
    }
}
