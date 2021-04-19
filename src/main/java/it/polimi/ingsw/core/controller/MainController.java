package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class MainController extends Thread{

    private Game currentGame;
    private Player currentPlayer;

    public MainController(Game currentGame)
    {
        this.currentGame = currentGame;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
