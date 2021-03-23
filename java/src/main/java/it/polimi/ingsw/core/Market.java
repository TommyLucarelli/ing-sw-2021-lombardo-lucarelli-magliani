package it.polimi.ingsw.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents the market structure, composed by (3 lines) * (4 columns) marbles
 * @author Martina Magliani
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
     * Every turn the player chooses a line or a column. A line contains 4 marbles.
     * @param line the number of the line selected by the player
     */
    public void pickLine(int line){
    }

    /**
     *Every turn the player chooses a line or a column. A column contains 3 marbles.
     * @param column the number of the column selected by the player
     */
    public void pickColumn(int column){}

    /**
     * This method transforms a marble into the related resource. It is used in pickLine and pickColumn.
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
