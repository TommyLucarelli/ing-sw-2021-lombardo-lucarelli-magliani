package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LeaderActivationController {
    @FXML
    Button yesBtn, noBtn;

    public void noAction(){
        activate(noBtn, false);
    }

    public void yesAction(){
        activate(yesBtn, true);
    }

    public void activate(Button source, boolean activation){
        JsonObject payload = new JsonObject();
        payload.addProperty("activation", activation);
        payload.addProperty("gameAction", "LEADER_ACTIVATION");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
