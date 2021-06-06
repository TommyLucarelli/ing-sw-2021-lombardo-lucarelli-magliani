package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller class for the leader cards popup scene.
 */
public class LeaderCardsController implements Initializable {
    @FXML
    ImageView l0, l1, w0, w1, w2, w3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int[] leaderCards = JavaFxApp.getManager().getMyself().getCompactBoard().getLeaderCards();
        ArrayList<ImageView> warehouseImages = new ArrayList<>(Arrays.asList(w0, w1, w2, w3));

        if(leaderCards[0] != 0){
            l0.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards[0] + ".png")));
        }
        if(leaderCards[1] != 0){
            l1.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards[1] + ".png")));
        }

        Resource[] warehouse = JavaFxApp.getManager().getMyself().getCompactBoard().getWarehouse();
        for (int i = 6; i < 10; i++) {
            if(warehouse[i].ordinal() != 5){
                warehouseImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + warehouse[i].ordinal() + ".png")));
            }
        }
    }
}
