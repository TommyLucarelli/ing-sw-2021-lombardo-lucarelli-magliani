package it.polimi.ingsw.net.server;

import java.util.ArrayList;

public class Lobby {
    private int id;
    private ArrayList<ClientHandler> players;

    public Lobby(){
        id = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
        players = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addPlayer(ClientHandler player) throws InvalidResponseException {
        if(players.size() == 4) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create/join a new lobby");
        else players.add(player);
    }

    public void removePlayer(int id) throws InvalidResponseException {
        for(ClientHandler player: players){
            if(player.getId() == id){
                players.remove(player);
                return;
            }
        }
        throw new InvalidResponseException("The player has already left the lobby!");
    }

    public void startGame(){}
}
