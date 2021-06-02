package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the create game scene.
 */
public class CreateGameController implements Initializable {
    @FXML
    ToggleButton btn1, btn2, btn3, btn4;

    ToggleGroup group = new ToggleGroup();
    int numPlayers = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn1.setToggleGroup(group);
        btn2.setToggleGroup(group);
        btn2.setSelected(true);
        btn3.setToggleGroup(group);
        btn4.setToggleGroup(group);
        btn1.setUserData(1);
        btn2.setUserData(2);
        btn3.setUserData(3);
        btn4.setUserData(4);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                numPlayers = (int) group.getSelectedToggle().getUserData();
            }
        });
    }

    /**
     * onAction method: sends to the server the selected action.
     */
    public void createGame(){
        JsonObject payload = new JsonObject();
        payload.addProperty("input", numPlayers);
        JavaFxApp.send(new ResponseMsg(null, MessageType.NUMBER_OF_PLAYERS, payload));

        Platform.runLater(() -> JavaFxApp.setRoot("waitplayers"));
    }
}
