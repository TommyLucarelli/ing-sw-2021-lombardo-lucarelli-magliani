package it.polimi.ingsw.core.controller;

/**
 * Interface for the classes that will be handle the different phase of the game.
 * The main method in these classes is runPhase(), that will be handle the flow of the specific phase
 */
public interface PhaseHandler {

    public boolean runPhase(); //boolean per controllare se l'azione è andata a buon fine o meno
}
