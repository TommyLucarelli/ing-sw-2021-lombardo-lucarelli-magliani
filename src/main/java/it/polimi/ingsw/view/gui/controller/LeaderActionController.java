package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for the Leader action scene.
 */
public class LeaderActionController implements DynamicController, Initializable {
    @FXML
    ImageView l0, l1;

    @FXML
    Rectangle r0, r1;

    @FXML
    Button actBtn, disBtn, comebackBtn, b0, b1;

    @FXML
    Text problem;

    int activeCard;
    int[] leaders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actBtn.setDisable(true);
        disBtn.setDisable(true);
    }

    @Override
    public void setData(JsonObject data) {
        Gson gson = new Gson();
        String json = data.get("leaders").getAsString();
        leaders = gson.fromJson(json, new TypeToken<int[]>(){}.getType());

        ArrayList<Integer> flags;
        json = data.get("flags").getAsString();
        flags = gson.fromJson(json, new TypeToken<ArrayList<Integer>>(){}.getType());
        b0.setDisable(true);
        b1.setDisable(true);

        if(leaders[0] != 0){
            b0.setDisable(false);
            l0.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaders[0] + ".png")));
            if(flags.contains(leaders[0])) b0.setDisable(true);
        }

        if(leaders[1] != 0){
            b1.setDisable(false);
            l1.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaders[1] + ".png")));
            if(flags.contains(leaders[1])) b1.setDisable(true);
        }

        if(data.has("problem")) problem.setVisible(true);
    }

    /**
     * onAction method: selects the first leader card.
     */
    public void active0(){
        r0. setVisible(true);
        r1.setVisible(false);
        actBtn.setDisable(false);
        disBtn.setDisable(false);
        activeCard = leaders[0];
    }

    /**
     * onAction method: selects the second leader card.
     */
    public void active1(){
        r0. setVisible(false);
        r1.setVisible(true);
        actBtn.setDisable(false);
        disBtn.setDisable(false);
        activeCard = leaders[1];
    }

    /**
     * onAction method: activates the selected leader card.
     */
    public void activate(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTION");
        payload.addProperty("cardID", activeCard);
        payload.addProperty("action", true);
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    /**
     * onAction method: discards the selected leader card.
     */
    public void discard(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTION");
        payload.addProperty("cardID", activeCard);
        payload.addProperty("action", false);
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    /**
     * onAction method: cancels the leader action.
     */
    public void comeback(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    /**
     * Closes the popup at the end of the action.
     */
    public void closePopup(){
        Stage stage = (Stage) actBtn.getScene().getWindow();
        stage.close();
    }
}
