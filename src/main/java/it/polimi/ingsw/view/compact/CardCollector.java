package it.polimi.ingsw.view.compact;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.core.model.DevCard;
import it.polimi.ingsw.core.model.LeaderCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * This class contains the Development and Leader cards present in the game. It will be used to retrieve the details
 * about a specific leader/development card sent by the server since the only information passed in the message will be
 * the card ID.
 */
public class CardCollector {
    private final List<DevCard> devCards;
    private final List<LeaderCard> leaderCards;
    private static final String leaderCardsURI = "src/main/resources/leadercards.json";
    private static final String devCardsURI = "src/main/resources/devcards.json";

    /**
     * Class constructor.
     * @throws FileNotFoundException if the URIs are invalid.
     */
    public CardCollector() throws FileNotFoundException {
        Type DEVCARD_TYPE = new TypeToken<List<DevCard>>() {}.getType();
        Type LEADERCARD_TYPE = new TypeToken<List<LeaderCard>>() {}.getType();
        Gson g = new Gson();

        JsonReader jsonReader = new JsonReader(new FileReader(leaderCardsURI));
        leaderCards = g.fromJson(jsonReader, LEADERCARD_TYPE);

        jsonReader = new JsonReader(new FileReader(devCardsURI));
        devCards = g.fromJson(jsonReader, DEVCARD_TYPE);
    }

    /**
     * Development cards getter.
     * @param id the id of the desired devCard.
     * @return the devCard having the specified id.
     */
    public DevCard getDevCard(int id) {
        if(id < 1 || id > 48) throw new IndexOutOfBoundsException();
        return devCards.get(id - 1);
    }

    /**
     * Leader cards getter.
     * @param id the id of the desired leader Card.
     * @return the leader card having the specified id.
     */
    public LeaderCard getLeaderCard(int id){
        if(id < 49 || id > 64) throw new IndexOutOfBoundsException();
        return leaderCards.get(id - 49);
    }
}
