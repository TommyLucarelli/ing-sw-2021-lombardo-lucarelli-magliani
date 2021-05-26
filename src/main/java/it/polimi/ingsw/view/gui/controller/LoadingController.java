package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements DynamicController, Initializable {
    @FXML
    Text loadMsg;

    @Override
    public void setData(JsonObject data) {
        loadMsg.setText(data.get("message").getAsString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), loadMsg);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }
}
