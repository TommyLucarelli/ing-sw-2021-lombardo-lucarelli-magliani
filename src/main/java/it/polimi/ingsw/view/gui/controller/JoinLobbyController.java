package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class JoinLobbyController {
    @FXML
    TextField lobbyId;

    public void joinLobby(){
        JsonObject payload = new JsonObject();
        if(!lobbyId.getText().isBlank()){
            payload.addProperty("input", lobbyId.getText());
            JavaFxApp.send(new ResponseMsg(null, MessageType.JOIN_GAME, payload));
            JavaFxApp.setRoot("loading");
        } else {
            lobbyId.setPromptText("Please enter a lobby Id!");
        }
    }
}
