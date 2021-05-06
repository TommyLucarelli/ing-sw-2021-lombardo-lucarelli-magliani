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

public class CardCollector {
    private List<DevCard> devCards;
    private List<LeaderCard> leaderCards;
    private static final String leaderCardsURI = "src/main/resources/leadercards.json";
    private static final String devCardsURI = "src/main/resources/devcards.json";

    public CardCollector() throws FileNotFoundException {
        Type DEVCARD_TYPE = new TypeToken<List<DevCard>>() {}.getType();
        Type LEADERCARD_TYPE = new TypeToken<List<LeaderCard>>() {}.getType();
        Gson g = new Gson();

        JsonReader jsonReader = new JsonReader(new FileReader(leaderCardsURI));
        leaderCards = g.fromJson(jsonReader, LEADERCARD_TYPE);

        jsonReader = new JsonReader(new FileReader(devCardsURI));
        devCards = g.fromJson(jsonReader, DEVCARD_TYPE);
    }

    public DevCard getDevCard(int id) {
        if(id < 1 || id > 48) throw new IndexOutOfBoundsException();
        return devCards.get(id - 1);
    }

    public LeaderCard getLeaderCard(int id){
        if(id < 49 || id > 64) throw new IndexOutOfBoundsException();
        return leaderCards.get(id - 49);
    }
}
