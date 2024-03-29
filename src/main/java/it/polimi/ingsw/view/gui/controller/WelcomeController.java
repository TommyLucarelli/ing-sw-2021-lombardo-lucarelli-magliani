package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller class for the welcome scene.
 */
public class WelcomeController implements DynamicController {
    @FXML
    Text welcomeMessage;

    @Override
    public void setData(JsonObject data) {
        if(data.has("error")) welcomeMessage.setText("The specified lobby doesn't exist!");
        else welcomeMessage.setText("Welcome, " + data.get("username").getAsString() + "!");
    }

    /**
     * onAction method: sends to the server the command to create a new game.
     */
    public void createGame() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 1);
        JavaFxApp.send(new ResponseMsg(null, MessageType.WELCOME_MESSAGE, payload));
    }

    /**
     * onAction method: sends to the server the command to join an existing game.
     */
    public void joinGame() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 2);
        JavaFxApp.send(new ResponseMsg(null, MessageType.WELCOME_MESSAGE, payload));
    }

}
