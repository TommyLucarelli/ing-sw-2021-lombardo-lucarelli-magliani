package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class MainController{

    private Game currentGame;
    private Player currentPlayer;
    private ArrayList<String> playerNames; //shuffle this and create the turn order


    public MainController()
    {
       //currentGame = new Game(); mi serve capire chi crea il controller per passare i dati giusti al game
    }

    public void run(){
        /**lancia beginHandler e lo itera per gli x player
        manda il messaggio UPDATE  a tutti i client con lo stato iniziale del gioco
        Iniziano i turni (verranno eseguiti in un ciclo che termina solo con l'ultimo giocatore e se è finito il gioco)
         DENTRO IL CICLO:
         1 - scelta gioca carta leader (MESSAGGIO)
         2 - crea classe LeaderCardHandler (opzionale)
         3 - scelta azione principale (MESSAGGIO)
         4 - crea classe di una delle tre azioni scelte (ci deve essere la possibilità di tornare indietro alla scelta)
         5 - scelta gioca carta leader (MESSAGGIO)
         6 - crea classe LeaderCardHandler (opzionale)
         7 - calcola il prossimo giocatore
         8 - manda a tutti l'UPDATE del gioco + prepara il prossimo giocatore che deve fare il turno
         FUORI DAL CICLO:
         Gestione fine partita
        **/
    }

    /*
    public Player computeNextPlayer(Player p){
        //passa il giocatore successivo
        //se è single mode restituisce Lorenzo che fa il suo turno speciale tipo
    }*/

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


}
