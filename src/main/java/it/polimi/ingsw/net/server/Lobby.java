package it.polimi.ingsw.net.server;

import it.polimi.ingsw.core.controller.MainController;
import it.polimi.ingsw.core.controller.PlayerHandler;

import java.util.ArrayList;

public class Lobby {
    private int id;
    private int lobbySize;
    private MainController controller;

    public Lobby(int lobbySize){
        id = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
        this.lobbySize = lobbySize;
        this.controller = new MainController(id, lobbySize);
    }

    public int getId() {
        return id;
    }

    public int getLobbySize() {
        return lobbySize;
    }

    public int getPlayersInLobby(){
        return controller.getPlayersInGame();
    }

    public boolean isGameInProgress(){
        return controller.isGameInProgress();
    }

    public PlayerHandler addPlayer(int id, String username, RequestManager manager) throws InvalidResponseException {
        return controller.addPlayer(id, username, manager);
    }
}
