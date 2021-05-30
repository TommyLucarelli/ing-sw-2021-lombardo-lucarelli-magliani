package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller class for the leader cards popup scene.
 */
public class LeaderCardsController implements DynamicController{
    @FXML
    ImageView l0, l1, w0, w1, w2, w3;

    @Override
    public void setData(JsonObject data) {
        if(data.get("l0").getAsInt() != 0){
            l0.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + data.get("l0").getAsInt() + ".png")));
        }
        if(data.get("l1").getAsInt() != 0){
            l1.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + data.get("l1").getAsInt() + ".png")));
        }

        ArrayList<ImageView> warehouseImages = new ArrayList<>(Arrays.asList(w0, w1, w2, w3));
        Gson gson = new Gson();
        String json = data.get("warehouse").getAsString();
        int[] warehouse = gson.fromJson(json, new TypeToken<int[]>(){}.getType());
        for (int i = 0; i < 4; i++) {
            if(warehouse[i] != 5){
                warehouseImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + warehouse[i] + ".png")));
            }
        }


    }
}
