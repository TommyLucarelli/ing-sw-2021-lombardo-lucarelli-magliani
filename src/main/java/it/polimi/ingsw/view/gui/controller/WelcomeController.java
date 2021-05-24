package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WelcomeController implements DynamicController {
    @FXML
    Text welcomeMessage;

    public void createGame() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 1);
        JavaFxApp.send(new ResponseMsg(null, MessageType.WELCOME_MESSAGE, payload));
    }

    public void joinGame() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 2);
        JavaFxApp.send(new ResponseMsg(null, MessageType.WELCOME_MESSAGE, payload));
    }

    @Override
    public void initData(JsonObject data) {
        welcomeMessage.setText("Welcome, " + data.get("username").getAsString() + "!");
    }
}
