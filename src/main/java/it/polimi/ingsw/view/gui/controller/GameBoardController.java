package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameBoardController implements DynamicController, Initializable {
    @FXML
    ImageView f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, w0, w1, w2, w3, w4, w5, slot11, slot12, slot13, slot21, slot22, slot23, slot31, slot32, slot33, fp1, fp2, fp3;

    @FXML
    Text sCoin, sStone, sShield, sServant;

    ArrayList<ImageView> faithTrack = new ArrayList<>();
    ArrayList<ImageView> warehouse = new ArrayList<>();
    ArrayList<ImageView> favourCards = new ArrayList<>();
    ArrayList<Text> strongbox = new ArrayList<>();
    ImageView[][] devCardSlot = new ImageView[3][3];
    CompactPlayer mySelf;
    CompactMarket compactMarket;
    CompactDevCardStructure compactDevCardStructure;
    HashMap<Integer, CompactPlayer> opponents = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        faithTrack.addAll(Arrays.asList(f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24));
        warehouse.addAll(Arrays.asList(w0, w1, w2, w3, w4, w5));
        favourCards.addAll(Arrays.asList(fp1, fp2, fp3));
        strongbox.addAll(Arrays.asList(sCoin, sStone, sShield, sServant));
        for(Text t: strongbox){
            t.setText("0");
        }
        devCardSlot[0][0] = slot11;
        devCardSlot[0][1] = slot12;
        devCardSlot[0][2] = slot13;
        devCardSlot[1][0] = slot21;
        devCardSlot[1][1] = slot22;
        devCardSlot[1][2] = slot23;
        devCardSlot[2][0] = slot31;
        devCardSlot[2][1] = slot32;
        devCardSlot[2][2] = slot33;

        mySelf = new CompactPlayer(JavaFxApp.getManager().getMyself().getPlayerID(), JavaFxApp.getManager().getMyself().getPlayerName());
    }

    @Override
    public void setData(JsonObject data) {
        if(data.get("gameAction").getAsString().equals("INITIAL_UPDATE")) handleInitialUpdate(data);
        else if(data.get("gameAction").getAsString().equals("UPDATE")) handleUpdate(data);
    }

    private void handleInitialUpdate(JsonObject update){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();
        int nextPlayerID;

        JsonObject payload = update.get("market").getAsJsonObject();
        nextPlayerID = update.get("nextPlayerID").getAsInt();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = update.get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = update.get("players").getAsJsonArray();
        for (JsonElement player: players) {
            if(player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()){
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<Resource[]>(){}.getType();
                Resource[] ware = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setWarehouse(ware);

            }else{
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(),player.getAsJsonObject().get("playerName").getAsString()));

                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setWarehouse(wa);
            }
        }

        updateView();

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    private void handleUpdate(JsonObject data){
        Gson gson = new Gson();
        JsonObject payload, payload2;

        int currentPlayerID = data.get("currentPlayerID").getAsInt();
        String message = data.get("message").getAsString();


        CompactPlayer player;
        if(mySelf.getPlayerID() == currentPlayerID)
            player = mySelf;
        else
            player = opponents.get(currentPlayerID); //da controllare

        String json = data.get("abilityActivationFlag").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] abilityActivationFlag = gson.fromJson(json, collectionType);
        player.getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

        json = data.get("discardedLeaderCards").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().removeDiscardedCards(gson.fromJson(json, collectionType));


        payload = data.get("market").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = data.get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        payload = data.get("player").getAsJsonObject();

        payload2 = payload.get("warehouse").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<Resource[]>(){}.getType();
        Resource[] ware = gson.fromJson(json, collectionType);
        player.getCompactBoard().setWarehouse(ware);

        payload2 = payload.get("strongbox").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().setStrongbox(gson.fromJson(json,collectionType));

        payload2 = payload.get("devCardSlots").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        player.getCompactBoard().setDevCardSlots(gson.fromJson(json,collectionType));

        JsonArray players = data.get("faithTracks").getAsJsonArray();
        CompactPlayer p2;
        for (JsonElement p: players) {
            if(mySelf.getPlayerID() == p.getAsJsonObject().get("playerID").getAsInt())
                p2 = mySelf;
            else
                p2 = opponents.get(p.getAsJsonObject().get("playerID").getAsInt());
            payload2 = p.getAsJsonObject().get("faithTrack").getAsJsonObject();
            p2.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
            json = payload2.get("favCards").getAsString();
            collectionType = new TypeToken<boolean[]>() {}.getType();
            boolean[] fav = gson.fromJson(json, collectionType);
            p2.getCompactBoard().setFavCards(fav);
        }

        if(data.has("lorenzoTrack")){
            JsonObject payload4 = data.get("lorenzoTrack").getAsJsonObject();
            player.getCompactBoard().setLorenzoIndex(payload4.get("index").getAsInt());
        }

        if(player.getPlayerID() != mySelf.getPlayerID() || opponents.size()==0)
            System.out.println("\n"+message+"\n");

        if(data.has("endMessage") && !mySelf.getLastTurn()){
            mySelf.setLastTurn(true);
            System.out.println(data.get("endMessage").getAsString());
        }

        updateView();

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    private void updateView(){
        for (int i = 0; i < mySelf.getCompactBoard().getFaithTrackIndex(); i++) {
            faithTrack.get(i).setImage(null);
            System.gc();
        }
        faithTrack.get(mySelf.getCompactBoard().getFaithTrackIndex()).setImage(new Image(getClass().getResourceAsStream("/images/resources/4.png")));

        for (int i = 0; i < 3; i++) {
            favourCards.get(i).setImage(
                    (mySelf.getCompactBoard().getFavCards()[i]) ?
                        new Image(getClass().getResourceAsStream("/images/resources/4.png")) :
                        new Image(getClass().getResourceAsStream("/images/resources/4.png"))
            );
        }

        for (int i = 0; i < 6; i++) {
            if(mySelf.getCompactBoard().getWarehouse()[i] == Resource.ANY){
                warehouse.get(i).setImage(null);
                System.gc();
            } else {
                warehouse.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + mySelf.getCompactBoard().getWarehouse()[i].ordinal() + ".png")));
            }
        }

        for (int i = 0; i < 4; i++) {
            strongbox.get(i).setText(String.valueOf(mySelf.getCompactBoard().getStrongbox()[i]));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(mySelf.getCompactBoard().getDevCardSlots()[i][j] != 0 &&
                        devCardSlot[i][j].getImage() != null)
                    devCardSlot[i][j].setImage(new Image(getClass().getResourceAsStream("/images/resources/" + mySelf.getCompactBoard().getWarehouse()[i].ordinal() + ".png")));
            }
        }
    }
}
