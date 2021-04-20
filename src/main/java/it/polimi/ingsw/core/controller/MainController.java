package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class MainController{

    private Game currentGame;
    private Player currentPlayer;


    public MainController()
    {
       //currentGame = new Game(); mi serve capire chi crea il controller per passare i dati giusti al game
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
