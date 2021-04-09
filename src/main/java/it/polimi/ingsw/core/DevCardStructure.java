package it.polimi.ingsw.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    protected DevCardStructure() throws FileNotFoundException{
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

        //TODO: (jack) si può snellire notevolmente questa parte
        for (int i = 0; i < cards.size(); i++){
            devCard = cards.pop();
            switch (devCard.getFlag().getColour()){
                case GREEN:
                    switch (devCard.getFlag().getLevel()){
                        case 1:
                            structure[0][0].push(devCard);
                            break;
                        case 2:
                            structure[1][0].push(devCard);
                            break;
                        case 3:
                            structure[2][0].push(devCard);
                            break;
                    }
                case BLUE:
                    switch (devCard.getFlag().getLevel()){
                        case 1:
                            structure[0][1].push(devCard);
                            break;
                        case 2:
                            structure[1][1].push(devCard);
                            break;
                        case 3:
                            structure[2][1].push(devCard);
                            break;
                    }
                case YELLOW:
                    switch (devCard.getFlag().getLevel()){
                        case 1:
                            structure[0][2].push(devCard);
                            break;
                        case 2:
                            structure[1][2].push(devCard);
                            break;
                        case 3:
                            structure[2][2].push(devCard);
                            break;
                    }
                case PURPLE:
                    switch (devCard.getFlag().getLevel()){
                        case 1:
                            structure[0][3].push(devCard);
                            break;
                        case 2:
                            structure[1][3].push(devCard);
                            break;
                        case 3:
                            structure[2][3].push(devCard);
                            break;
                    }
            }
        }
    }

    public DevCard drawCard(int row, int column){
        return (DevCard) structure[row][column].pop();
    }


}
