package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller class for the registration controller.
 */
public class RegistrationController implements DynamicController{
    @FXML
    TextField username;

    @FXML
    Text errorMsg;

    @Override
    public void setData(JsonObject data) {
        errorMsg.setVisible(data.get("error").getAsString().equals("true"));
    }

    /**
     * onAction method: closes the application.
     */
    public void quitAction(){
        JavaFxApp.close();
    }

    /**
     * onAction method: sends to the server the entered username.
     */
    public void playAction(){
        JsonObject payload = new JsonObject();
        if(!username.getText().strip().isBlank()){
            payload.addProperty("input", username.getText());
            JavaFxApp.send(new ResponseMsg(null, MessageType.REGISTRATION_MESSAGE, payload));
            //JavaFxApp.setRoot("loading");
        } else {
            username.setText(null);
            username.setPromptText("Please enter a username!");
        }
    }
}
