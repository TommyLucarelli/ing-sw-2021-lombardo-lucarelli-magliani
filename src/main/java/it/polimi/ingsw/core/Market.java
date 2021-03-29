package it.polimi.ingsw.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents the market structure, composed by (3 lines) * (4 columns) marbles
 * @author Martina Magliani
 */

public class Market {

    Marble[][] structure;
    Marble reserveMarble, x;
    ArrayList<Marble> initialStructure = new ArrayList<Marble>();
    ArrayList<Resource> resources = new ArrayList<Resource>();
    Random r = new Random();
    int pos, count = 12;

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
        return structure.clone();
    }

    /**
     * Getter method.
     * @param line the number of the line selected by the player
     * @return all the obtained resources from the selected line.
     */

    public ArrayList<Resource> getLine(int line){
        for (int i=0; i<4; i++){
            resources.add(MarbleToResource(structure[line][i]));
        }
        return resources;
    }

    /**
     * Getter method.
     * @param column the number of the column selected by the player
     * @return obtained resources from the selected column.
     */
    public ArrayList<Resource> getColumn(int column){
        for (int i=0; i<3; i++){
            resources.add(MarbleToResource(structure[i][column]));
        }
        return resources;
    }

    public Resource MarbleToResource (Marble marble){
        switch (marble){
            case GREY:
                return Resource.STONE;
            case BLUE:
                return Resource.SHIELD;
            case RED:
                return Resource.FAITH;
            case YELLOW:
                return Resource.COIN;
            case PURPLE:
                return Resource.SERVANT;
            case WHITE:
                return Resource.ANY;
        }
        return null;
    }
    //TODO: (group) selezionare i metodi utili alla classe.

}
