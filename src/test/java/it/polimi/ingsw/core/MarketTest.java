package it.polimi.ingsw.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void testGetStructure() {
    }

    @Test
    public void testGetLine() {
        Market m = new Market();
        Marble[][] matrix = new Marble[3][4];
        ArrayList<Resource> resources;

        matrix = m.getStructure();
        //System.out.println("reserveMarble" + m.reserveMarble.toResource());
        for (int i=0; i<4; i++){
            System.out.println("matrice = " + matrix[1][i].toResource());
        }
        resources = m.getLine(1);
        //System.out.println("reserveMarble" + m.reserveMarble.toResource());
        for (int i=0; i<4; i++){
            System.out.println("matrice = " + matrix[1][i].toResource());
        }
        /**for (int i=0; i<4; i++){
            System.out.println("res = " + resources.get(i));
        }

        for (int i=0; i<4; i++) {
            assertEquals(matrix[1][i].toResource(), resources.get(i));
        }
         **/
    }

    @Test
    public void testGetColumn() {
    }
}