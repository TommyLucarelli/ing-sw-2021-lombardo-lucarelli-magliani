package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrationController {
    @FXML
    TextField username;

    public void quitAction(){
        Gui.close();
    }

    public void playAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("input", username.getText());
        Gui.send(new ResponseMsg(null, MessageType.REGISTRATION_MESSAGE, payload));
        Gui.setRoot("loading");
    }
}
