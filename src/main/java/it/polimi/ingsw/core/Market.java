package it.polimi.ingsw.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents the market structure, composed by (3 lines) * (4 columns) marbles
 * plus one marble that is the reserveMarble
 * @author Martina Magliani
 */

public class Market {

    private Marble[][] structure;
    private Marble reserveMarble,rM, x;
    private ArrayList<Marble> initialStructure = new ArrayList<Marble>();
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private Random r = new Random();
    private int pos, count = 12;

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
     public ArrayList<Resource> getStructure() {
        Marble[][] m = new Marble[3][4];
        for(int i = 0; i< structure.length; i++){
            for (int j = 0; j < structure[i].length; j++){
                m[i][j] = structure[i][j];
            }
        return m;
    }

    /**
     * Getter method.
     * @param line the number of the line selected by the player
     * @return all the obtained resources from the selected line.
     */

    public ArrayList<Resource> getLine(int line) {
        for (int i = 0; i < 4; i++) {
            resources.add(structure[line][i].toResource());
        }

        rM = reserveMarble;
        reserveMarble = structure[line][0];
        for (int i=0; i<3; i++){
            structure[line][i] = structure[line][i+1];
        }
        structure[line][3] = rM;

        return resources;
    }

    /**
     * Getter method.
     * @param column the number of the column selected by the player
     * @return obtained resources from the selected column and modified the market structure
     */
    public ArrayList<Resource> getColumn(int column){
        for (int i=0; i<3; i++){
            resources.add(structure[i][column].toResource());
        }
        structure[2][column] = reserveMarble;
        reserveMarble = structure[0][column];
        for (int i=0; i<2; i++){
            structure[i][column] = structure[i+1][column];
        }

        return (ArrayList<Resource>) resources.clone();
    }

    //TODO: (group) selezionare i metodi utili alla classe.

}
