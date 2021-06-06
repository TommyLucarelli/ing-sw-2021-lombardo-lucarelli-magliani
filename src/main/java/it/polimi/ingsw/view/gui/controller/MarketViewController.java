package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MarketViewController implements Initializable {
    @FXML
    ImageView m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ImageView> marbleImages = new ArrayList<>(Arrays.asList(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12));

        int[] marbles = JavaFxApp.getManager().getCompactMarket().getMarket();

        for (int i = 0; i < 13; i++) {
            marbleImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/marbles/" + marbles[i] + ".png")));
        }
    }
}
