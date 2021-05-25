package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class JoinLobbyController {
    @FXML
    TextField lobbyId;

    public void joinLobby(){
        JsonObject payload = new JsonObject();
        if(!lobbyId.getText().isBlank() && lobbyId.getText().length() == 5){
            payload.addProperty("input", lobbyId.getText());
            JavaFxApp.send(new ResponseMsg(null, MessageType.JOIN_GAME, payload));
            JavaFxApp.setRoot("loading");
        } else {
            lobbyId.setPromptText("Please enter a valid lobby Id!");
        }
    }

    public void aboutDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Progetto di Ingegneria del Software - A.A. 2020/21\n" +
                "Autori: Martina Magliani, Giacomo Lombardo, Tommaso Lucarelli");
        alert.showAndWait();
    }

    public void helpDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText(null);
        alert.setContentText("Enter the lobby ID of the lobby you want to join.\n" +
                "A valid lobby ID is a number between 10000 and 99999");
        alert.showAndWait();
    }

    public void quit(){
        JavaFxApp.close();
    }
}
