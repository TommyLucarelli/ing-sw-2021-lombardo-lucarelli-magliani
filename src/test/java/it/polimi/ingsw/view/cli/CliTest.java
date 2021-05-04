package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.compact.CompactMarket;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliTest {

    @Test
    public void printMarket() {
        Cli cli = new Cli();
        cli.printMarket();
    }

    @Test
    public void printDevCardSlot() {
    }

    @Test
    public void printDevCardStructure() {
    }

    @Test
    public void printWarehouse() {
        Cli cli = new Cli();
        cli.printWarehouse();
    }

    @Test
    public void printStrongbox() {
        Cli cli = new Cli();
        cli.printStrongbox();
    }

    @Test
    public void printFaithTrack() {
        Cli cli = new Cli();
        cli.printFaithTrack();
    }

    @Test
    public void printPersonalBoard() {
        Cli cli = new Cli();
        cli.printPersonalBoard();
    }
}