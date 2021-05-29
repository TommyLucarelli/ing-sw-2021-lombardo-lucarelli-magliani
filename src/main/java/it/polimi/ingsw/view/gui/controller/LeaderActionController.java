package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LeaderActionController implements DynamicController, Initializable {
    @FXML
    ImageView l0, l1;

    @FXML
    Rectangle r0, r1;

    @FXML
    Button actBtn, disBtn, comebackBtn, b0, b1;

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
        if(leaders[0] != 0){
            l0.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaders[0] + ".png")));
        }
        if(leaders[1] != 0){
            l1.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaders[1] + ".png")));
        }
    }

    public void active0(){
        r0. setVisible(true);
        r1.setVisible(false);
        actBtn.setDisable(false);
        disBtn.setDisable(false);
        activeCard = leaders[0];
    }

    public void active1(){
        r0. setVisible(false);
        r1.setVisible(true);
        actBtn.setDisable(false);
        disBtn.setDisable(false);
        activeCard = leaders[1];
    }

    public void activate(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTION");
        payload.addProperty("cardID", activeCard);
        payload.addProperty("action", true);
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    public void discard(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTION");
        payload.addProperty("cardID", activeCard);
        payload.addProperty("action", true);
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    public void comeback(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        closePopup();
    }

    public void closePopup(){
        Stage stage = (Stage) actBtn.getScene().getWindow();
        stage.close();
    }
}
