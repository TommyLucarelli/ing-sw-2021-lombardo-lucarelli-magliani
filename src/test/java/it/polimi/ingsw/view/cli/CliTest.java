package it.polimi.ingsw.view.cli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliTest {
    @Test
    public void testChooseStartLeaders() {
        int[] leaderCards = {1, 2, 3, 4};
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_LEADERS");
        payload.addProperty("message", "Choose the 2 leader cards between the 4 drafted!");
        JsonArray array = new JsonArray();
        for (int i = 0; i < leaderCards.length; i++) {
            array.add(leaderCards[i]);
        }
        payload.add("leaderCards", array);
        RequestMsg sampleRequest = new RequestMsg(MessageType.GAME_MESSAGE, payload);

        System.out.println(sampleRequest.getPayload().get("leaderCards").getAsJsonArray());
    }
}