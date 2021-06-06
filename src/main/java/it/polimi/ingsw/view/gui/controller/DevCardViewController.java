package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DevCardViewController implements Initializable {
    @FXML
    ImageView c11, c12, c13, c14, c21, c22, c23, c24, c31, c32, c33, c34;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ImageView> cards = new ArrayList<>(Arrays.asList(c11, c12, c13, c14, c21, c22, c23, c24, c31, c32, c33, c34));

        int[][] structure = JavaFxApp.getManager().getCompactDevCardStructure().getDevCardStructure();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(structure[i][j] != 0)
                    cards.get((i * 4) + j).setImage(new Image(getClass().getResourceAsStream("/images/cards/" + structure[i][j] + ".png")));
            }
        }
    }
}
