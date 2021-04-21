package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class MainController{

    private Game currentGame;
    private Player currentPlayer;
    private ArrayList<Player> players;


    public MainController()
    {
       //currentGame = new Game(); mi serve capire chi crea il controller per passare i dati giusti al game
    }

    public void run(){
        //inizio gioco + evoluzione turni
    }

    /*
    public Player computeNextPlayer(Player p){
        //passa il giocatore successivo
    }*/

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


}
