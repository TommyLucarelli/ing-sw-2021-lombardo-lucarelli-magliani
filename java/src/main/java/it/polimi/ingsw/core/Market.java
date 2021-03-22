package it.polimi.ingsw.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represent the market structure, composed by (3 line) * (4 column) marbles
 * @author martina
 */

public class Market {

    Marble[][] structure;
    int line, column, pos;
    Marble reserveMarble, x;
    ArrayList<Marble> initialStructure = new ArrayList<Marble>();
    Random r = new Random();
    int count = 12;

    /**
     * Class constructor
     */
    public Market(){

        initialStructure.add(Marble.WHITE);
        initialStructure.add(Marble.WHITE);
        initialStructure.add(Marble.WHITE);
        initialStructure.add(Marble.WHITE);
        initialStructure.add(Marble.BLUE);
        initialStructure.add(Marble.BLUE);
        initialStructure.add(Marble.GREY);
        initialStructure.add(Marble.GREY);
        initialStructure.add(Marble.YELLOW);
        initialStructure.add(Marble.YELLOW);
        initialStructure.add(Marble.PURPLE);
        initialStructure.add(Marble.PURPLE);
        initialStructure.add(Marble.RED);

        structure = new Marble[3][4];
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                pos = r.nextInt(count);
                x = initialStructure.get(pos);
                initialStructure.remove(pos);
                structure[i][j] = x;
                count--;
            }
        }
        reserveMarble = initialStructure.get(0);
    }

    /**
     * Getter Method
     * @return the structure of the market place
     */
    public Marble[][] getStructure() {
        return structure;
    }

    /**
     * Every turn the player choose a line or a column. A line contains 4 marbles.
     * @param line
     */
    public void pickLine(int line){
    }

    /**
     *Every turn the player choose a line or a column. A column contains 3 marbles.
     * @param column
     */
    public void pickColumn(int column){}

    /**
     * This method transform a marble to the related resource. Is used in pickLine e pickColumn.
     */
    private Resource MarbleToResource(Marble marble){
        switch (marble) {
            case RED:
                return Resource.FAITH;
            case BLUE:
                return Resource.SHIELD;
            case GREY:
                return Resource.STONE;
            case YELLOW:
                return Resource.COIN;
            case WHITE:
                return Resource.ANY;
            case PURPLE:
                return Resource.SERVANT;
        }
        return null;
    }

}
