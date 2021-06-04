package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlaceDevCardController implements Initializable {
    @FXML
    ImageView slot11, slot12, slot13, slot21, slot22, slot23, slot31, slot32, slot33;

    @FXML
    Rectangle r11, r12, r13, r21, r22, r23, r31, r32, r33;

    ArrayList<Rectangle> rectangles = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ImageView> slots = new ArrayList<>(Arrays.asList(slot11, slot12, slot13, slot21, slot22, slot23, slot31, slot32, slot33));
        rectangles.addAll(Arrays.asList(r11, r12, r13, r21, r22, r23, r31, r32, r33));

        int[][] mySlots = JavaFxApp.getManager().getMyself().getCompactBoard().getDevCardSlots();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(mySlots[i][j] != 0)
                    slots.get((i * 3) + j).setImage(new Image(getClass().getResourceAsStream("/images/cards/" + mySlots[i][j] + ".png")));
                else {
                    rectangles.get((i * 3) + 1).setVisible(true);
                    rectangles.get((i * 3) + 1).setDisable(false);
                    break;
                }
            }
        }


    }

    public void place1(){
        choice(1);
    }

    public void place2(){
        choice(2);
    }

    public void place3(){
        choice(3);
    }

    public void choice(int spot){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "DEVCARD_PLACEMENT");
        payload.addProperty("index", spot - 1);

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) slot11.getScene().getWindow();
        stage.close();
    }

}
