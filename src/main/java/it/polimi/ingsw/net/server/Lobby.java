package it.polimi.ingsw.net.server;

import it.polimi.ingsw.core.controller.MainController;

import java.util.ArrayList;

public class Lobby {
    private int id;
    private int lobbySize;
    private ArrayList<Integer> playersId;
    private MainController controller;
    private boolean gameInProgress;

    public Lobby(int playerId, int lobbySize){
        id = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
        this.lobbySize = lobbySize;
        playersId = new ArrayList<Integer>();
        playersId.add(playerId);
        gameInProgress = false;
    }

    public int getId() {
        return id;
    }

    public int getLobbySize() {
        return lobbySize;
    }

    public int getPlayersInLobby(){
        return playersId.size();
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void addPlayer(int id) throws InvalidResponseException {
        if(playersId.size() == 4) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create/join a new lobby");
        else playersId.add(id);
    }

    public void removePlayer(int id) throws InvalidResponseException {
        for(Integer playerId: playersId){
            if(playerId == id){
                playersId.remove(playerId);
                return;
            }
        }
        throw new InvalidResponseException("The player has already left the lobby!");
    }

    public void startGame(){
        controller = new MainController();
        gameInProgress = true;
    }
}
