package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents the market structure, composed by (3 lines) * (4 columns) marbles
 * plus one marble that is the reserveMarble
 * @author Martina Magliani
 */

public class Market {

    private Marble[][] structure;
    private Marble reserveMarble;

    /**
     * Class constructor
     */
    public Market(){
        ArrayList<Marble> initialStructure = new ArrayList<Marble>();

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

        int pos, count = 12;
        Random r = new Random();

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                pos = r.nextInt(count);
                Marble x = initialStructure.get(pos);
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
     * @return the structure of the market as an array: the first 4 slots are the first row, and so on...
     */
    public Marble[] getStructureAsArray(){
         Marble[] structureArray = new Marble[12];
         for(int i = 0; i < 12; i++){
             structureArray[i] = structure[i/4][i%4];
         }
         return structureArray;
    }

    /**
     * Getter method.
     * @param line the selected line
     * @return an array containing the marbles of the specified line
     */
    public Marble[] getLine(int line){
         Marble[] result = new Marble[4];
         for ( int i=0; i<4; i++){
             result[i] = structure[line][i];
         }
         return result;
    }

    /**
     * Getter method.
     * @param column the selected columnn
     * @return an array containing the marbles of the specified column
     */
    public Marble[] getColumn(int column){
        Marble[] result = new Marble[3];
        for ( int i=0; i<3; i++){
            result[i] = structure[i][column];
        }
        return result;
    }

    /**
     * Getter method.
     * @return the marble not inserted into the market structure.
     */
    public Marble getReserveMarble(){
        return reserveMarble;
    }

    /**
     * Inserts the reserve marble into the specified line, shifting its marbles and getting a new reserve marble.
     * @param line the number of the line selected by the player.
     * @return all the obtained resources from the selected line.
     */
    public ArrayList<Resource> updateLineAndGetResources(int line) {
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            resources.add(structure[line][i].toResource());
        }

        Marble rM = reserveMarble;
        reserveMarble = structure[line][0];
        for (int i=0; i<3; i++){
            structure[line][i] = structure[line][i+1];
        }
        structure[line][3] = rM;

        return resources;
    }

    /**
     * Inserts the reserve marble into the specified column, shifting its marbles and getting a new reserve marble.
     * @param column the number of the column selected by the player.
     * @return obtained resources from the selected column.
     */
    public ArrayList<Resource> updateColumnAndGetResources(int column){
        ArrayList<Resource> resources = new ArrayList<>();
        for (int i=0; i<3; i++){
            resources.add(structure[i][column].toResource());
        }

        Marble rM = reserveMarble;
        reserveMarble = structure[0][column];
        for (int i=0; i<2; i++){
            structure[i][column] = structure[i+1][column];
        }
        structure[2][column] = rM;

        return resources;
    }

    /**
     * Getter method.
     * @param row the row of the desired marble
     * @param column the column of the desired marble
     * @return the marble at the specified coordinates.
     */
    public Marble getMarble(int row, int column){
        return structure[row][column];
    }

    public JsonObject toCompactMarket(){
        int [] compact = new int[12];
        int x = reserveMarble.ordinal();

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                compact[i+j] = structure[i][j].ordinal();
            }
        }

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(compact);
        payload.addProperty("structure", json);
        payload.addProperty("reserveMarble", x);

        return payload;
    }

}
