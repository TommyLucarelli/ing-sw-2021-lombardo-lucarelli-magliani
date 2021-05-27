package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Stack;

/**
 * Class representing the structure of Development Cards generated at the beginning of the game
 * @author Giacomo Lombardo
 */
public class DevCardStructure {
    // memo: green-blue-yellow-purple 3 rows, 4 columns
    private Stack<DevCard>[][] structure;
    private static final Type CARD_TYPE = new TypeToken<Stack<DevCard>>() {}.getType();
    private static final String filename = "src/main/resources/devcards.json";

    /**
     * Class constructor. Initializes the structure and then generates the development cards.
     */
    public DevCardStructure() throws FileNotFoundException{
        Gson g = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(filename));
        Stack<DevCard> cards = g.fromJson(jsonReader, CARD_TYPE);

        Collections.shuffle(cards);

        DevCard devCard;

        structure = new Stack[3][4];
        for (int i = 0; i < 3; i++){
            for (int ii = 0; ii < 4; ii++){
                structure[i][ii] = new Stack<DevCard>();
            }
        }
        int i;
        for (i = 0; i < 48; i++){
            devCard = cards.pop();
            switch (devCard.getFlag().getColour()){
                case GREEN:
                    structure[devCard.getFlag().getLevel() - 1][0].push(devCard);
                    break;
                case BLUE:
                    structure[devCard.getFlag().getLevel() - 1][1].push(devCard);
                    break;
                case YELLOW:
                    structure[devCard.getFlag().getLevel() - 1][2].push(devCard);
                    break;
                case PURPLE:
                    structure[devCard.getFlag().getLevel() - 1][3].push(devCard);
                    break;
            }
        }
    }

    /**
     * Getter method. Returns the card on top of the stack at the specified coordinates without removing it.
     * @param row the row of the stack
     * @param column the column of the stack
     * @return the DevCard at the specified coordinates.
     */
    public DevCard getTopCard(int row, int column) throws IndexOutOfBoundsException{
        if(structure[row][column].size() == 0) throw new IndexOutOfBoundsException();
        return (DevCard) structure[row][column].peek();
    }

    /**
     * Getter method.
     * @param row the row of the structure.
     * @param column the column of the structure.
     * @return the amount of cards in the stack at the specified coordinates.
     */
    public int stackSize(int row, int column){
        return structure[row][column].size();
    }

    /**
     * Pop method. Returns the card on top of the stack at the specified coordinates and removes it from the stack.
     * @param row the row of the stack
     * @param column the column of the stack
     * @return the DevCard at the specified coordinates.
     */
    public DevCard drawCard(int row, int column) throws IndexOutOfBoundsException{
        if(structure[row][column].size() == 0) throw new IndexOutOfBoundsException();
        return (DevCard) structure[row][column].pop();
    }

    /**
     * Method used for network communication.
     * @return a brief representation of the structure as a string containing the id of the dev cards at the top of
     * each stack.
     * @deprecated
     */
    public String toStringTopStructure(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                str.append(structure[i][j].size() > 0 ? structure[i][j].peek().getId() : 0);
                str.append("-");
            }
            str.append(";");
        }
        str.append("}");
        return str.toString();
    }

    /**
     * Method for network communication.
     * @return a brief representation of the structure as a Json Object containing the id of the dev cards at the top
     * of each stack.
     */
    public JsonObject toCompactDevCardStructure(){
        int[][] mat = new int[3][4];

        for (int i = 0; i < 3; i++){
            for (int ii = 0; ii < 4; ii++){
                try{
                    mat[i][ii] = getTopCard(i,ii).getId();
                } catch (IndexOutOfBoundsException e){
                    mat[i][ii] = 0;
                }
            }
        }

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(mat);
        payload.addProperty("structure", json);
        //capire come serializza la matrice
        return payload;
    }

    /**
     * Method to discard a card when a devCard token is activated
     * @param colour of the flag
     * @return boolean, if it's true the lastTurn is activated
     */
    public Boolean discardSingle(Colour colour) {
        int i = 0;
        Boolean flag;
        for (int j = 0; j < 2; j++) {
            do {
                flag = false;
                try {
                    drawCard(i, colour.ordinal());
                } catch (IndexOutOfBoundsException e) {
                    i++;
                    flag = true;
                }
            }while(flag && i<3);
        }

        return i == 3; //se ritorna true vuol dire che è finita una colonna -> ENDGAME
    }
}
