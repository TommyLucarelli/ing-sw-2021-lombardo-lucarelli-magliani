package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.view.compact.CompactPlayer;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the game board scene.
 */
public class GameBoardController implements DynamicController, Initializable {
    @FXML
    ImageView f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, w0, w1, w2, w3, w4, w5, slot11, slot12, slot13, slot21, slot22, slot23, slot31, slot32, slot33, fp1, fp2, fp3;

    @FXML
    Text sCoin, sStone, sShield, sServant;

    @FXML
    Button player2, player3, player4;

    @FXML
    TextArea updates;

    ArrayList<ImageView> faithTrack = new ArrayList<>();
    ArrayList<ImageView> warehouse = new ArrayList<>();
    ArrayList<ImageView> favourCards = new ArrayList<>();
    ArrayList<Button> playerButtons = new ArrayList<>();
    ArrayList<Text> strongbox = new ArrayList<>();
    ImageView[][] devCardSlot = new ImageView[3][3];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        faithTrack.addAll(Arrays.asList(f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24));
        warehouse.addAll(Arrays.asList(w0, w1, w2, w3, w4, w5));
        favourCards.addAll(Arrays.asList(fp1, fp2, fp3));
        strongbox.addAll(Arrays.asList(sCoin, sStone, sShield, sServant));
        playerButtons.addAll(Arrays.asList(player2, player3, player4));

        for(Button playerButton: playerButtons) playerButton.setVisible(false);

        devCardSlot[0][0] = slot11;
        devCardSlot[0][1] = slot12;
        devCardSlot[0][2] = slot13;
        devCardSlot[1][0] = slot21;
        devCardSlot[1][1] = slot22;
        devCardSlot[1][2] = slot23;
        devCardSlot[2][0] = slot31;
        devCardSlot[2][1] = slot32;
        devCardSlot[2][2] = slot33;
    }

    @Override
    public void setData(JsonObject data) {
        Gson gson = new Gson();
        for (int i = 0; i < data.get("faithPoints").getAsInt(); i++) {
            if(faithTrack.get(i).getImage() == null){
                faithTrack.get(i).setImage(null);
                System.gc();
            }
        }
        faithTrack.get(data.get("faithPoints").getAsInt()).setImage(new Image(getClass().getResourceAsStream("/images/resources/4.png")));

        boolean[] updatedFavourCards = gson.fromJson(data.get("favourCards").getAsString(), new TypeToken<boolean[]>(){}.getType());
        for (int i = 0; i < 3; i++) {
            favourCards.get(i).setImage(
                    (updatedFavourCards[i]) ?
                            new Image(getClass().getResourceAsStream("/images/faithtrack/" + (i + 1) +"true.png")) :
                            new Image(getClass().getResourceAsStream("/images/faithtrack/" + (i + 1) +"false.png"))
            );
        }

        Resource[] updatedWarehouse = gson.fromJson(data.get("warehouse").getAsString(), new TypeToken<Resource[]>(){}.getType());
        for (int i = 0; i < 6; i++) {
            if(updatedWarehouse[i] == Resource.ANY){
                warehouse.get(i).setImage(null);
                System.gc();
            } else {
                warehouse.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + updatedWarehouse[i].ordinal() + ".png")));
            }
        }

        int[] updatedStrongBox = gson.fromJson(data.get("strongbox").getAsString(), new TypeToken<int[]>(){}.getType());
        for (int i = 0; i < 4; i++) {
            strongbox.get(i).setText(String.valueOf(updatedStrongBox[i]));
        }

        int[][] updatedDevCardSlots = gson.fromJson(data.get("devCardSlots").getAsString(), new TypeToken<int[][]>(){}.getType());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(updatedDevCardSlots[i][j] != 0 &&
                        devCardSlot[i][j].getImage() == null)
                    devCardSlot[i][j].setImage(new Image(getClass().getResourceAsStream("/images/cards/" + updatedDevCardSlots[i][j] + ".png")));
            }
        }

        ArrayList<String> opponents = gson.fromJson(data.get("opponents").getAsString(), new TypeToken<ArrayList<String>>(){}.getType());
        if(opponents.size() == 0){
            playerButtons.get(0).setText("Lorenzo\nFaith Points: " + JavaFxApp.getManager().getMyself().getCompactBoard().getLorenzoIndex());
            playerButtons.get(0).setVisible(true);
            playerButtons.get(0).setDisable(false);
            playerButtons.get(0).setOnAction(Event::consume);
        } else {
            for (int i = 0; i < opponents.size(); i++) {
                playerButtons.get(i).setText(opponents.get(i));
                playerButtons.get(i).setVisible(true);
                playerButtons.get(i).setDisable(false);
                int index = i;
                playerButtons.get(i).setOnAction(evt -> showPlayer(JavaFxApp.getManager().getOpponent(index)));
            }
        }

        updates.setText(data.get("updates").getAsString());
    }

    /**
     * onAction method: opens a popup that shows the player's leader cards.
     */
    public void showLeaders(){
        JavaFxApp.showPopup("leadercards", true);
    }

    /**
     * onAction method: opens a popup that shows the market.
     */
    public void showMarket(){
        JavaFxApp.showPopup("marketview", true);
    }

    /**
     * onAction method: opens a popup that shows the development card structure.
     */
    public void showDevCards(){
        JavaFxApp.showPopup("devcardview", true);
    }

    /**
     * Show another player's board.
     * @param player the index of the player in the opponents HashMap.
     */
    private void showPlayer(CompactPlayer player){
        if(player == null) return;
        JsonObject data = new JsonObject();
        Gson gson = new Gson();

        data.addProperty("warehouse", gson.toJson(player.getCompactBoard().getWarehouse()));
        data.addProperty("strongbox", gson.toJson(player.getCompactBoard().getStrongbox()));
        data.addProperty("devCardSlots", gson.toJson(player.getCompactBoard().getDevCardSlots()));
        data.addProperty("faithPoints", player.getCompactBoard().getFaithTrackIndex());
        data.addProperty("favourCards", gson.toJson(player.getCompactBoard().getFavCards()));
        Platform.runLater(() -> {
            JavaFxApp.showPopup("opponentboard", true);
            JavaFxApp.setData(data);
        });
    }
}
