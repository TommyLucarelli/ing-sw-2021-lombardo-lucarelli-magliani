package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller class for the reconnection scene.
 */
public class ReconnectionController implements DynamicController {
    @FXML
    Text welcomeMessage;

    @Override
    public void setData(JsonObject data) {
        if(data.has("error")) welcomeMessage.setText("The specified lobby doesn't exist!");
        else welcomeMessage.setText("Welcome, back" + data.get("username").getAsString() + "!");
    }

    /**
     * onAction method: sends to the server the command to resume the game.
     */
    public void resumeAction() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 1);
        JavaFxApp.send(new ResponseMsg(null, MessageType.RECONNECTION_MESSAGE, payload));
    }

    /**
     * onAction method: sends to the server the command to start a new game.
     */
    public void newGameAction() {
        JsonObject payload = new JsonObject();
        payload.addProperty("input", 2);
        JavaFxApp.send(new ResponseMsg(null, MessageType.RECONNECTION_MESSAGE, payload));
    }

}