package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainChoiceController {
    @FXML
    Button prodBtn, marketBtn, devBtn;

    public void prodAction(){
        action(prodBtn, "production");
    }

    public void marketAction(){
        action(marketBtn, "market");
    }

    public void devAction(){
        action(devBtn, "buyDevCard");
    }


    public void action(Button source, String action){
        JsonObject payload = new JsonObject();
        payload.addProperty("actionChoice", action);
        payload.addProperty("gameAction", "MAIN_CHOICE");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
