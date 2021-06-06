package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Recipe;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {
    @FXML
    ImageView s0, s1, s2, e0, e1, eres0, eres1, basic0, basic1, basic2;

    @FXML
    Rectangle r0, r1, r2, r3, r4, r5;

    @FXML
    Text extratext0, extratext1;

    int eres0index = 0, eres1index = 0;

    int[] devCards = new int[3];
    ArrayList<Integer> productions = new ArrayList<>();
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    int[] basicProd = new int[3];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        e0.setVisible(false);
        e1.setVisible(false);
        extratext0.setVisible(false);
        extratext1.setVisible(false);

        ArrayList<ImageView> slots = new ArrayList<>(Arrays.asList(s0, s1, s2));
        rectangles.addAll(Arrays.asList(r0, r1, r2, r3, r4, r5));
        for (int i = 0; i < 3; i++) {
            basicProd[i] = 0;
        }

        for (int i = 0; i < 3; i++) {
            slots.get(i).setVisible(false);
            for (int j = 0; j < 3; j++) {
                if(JavaFxApp.getManager().getMyself().getCompactBoard().getDevCardSlots()[i][j] != 0)
                    devCards[i] = JavaFxApp.getManager().getMyself().getCompactBoard().getDevCardSlots()[i][j];
            }
            if(devCards[i] != 0) {
                slots.get(i).setImage(new Image(getClass().getResourceAsStream("/images/cards/" + devCards[i] + ".png")));
                slots.get(i).setVisible(true);
            }
        }

        if(JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[6] != 0){
            extratext0.setVisible(true);
            eres0.setVisible(true);
            e0.setVisible(true);
            e0.setImage(new Image(getClass().getResourceAsStream("/images/production/special" +
                    (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[6] - 61) + ".png")));
            if(JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[7] != 0){
                e1.setVisible(true);
                extratext1.setVisible(true);
                eres1.setVisible(true);
                e1.setImage(new Image(getClass().getResourceAsStream("/images/production/special" +
                        (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[7] - 61) + ".png")));
            }
        }
    }

    public void comebackAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) eres0.getScene().getWindow();
        stage.close();
    }

    public void confirmAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
        Gson gson = new Gson();
        payload.addProperty("productions", gson.toJson(productions));
        if(productions.contains(1)){
            ArrayList<ResourceQty> input = new ArrayList<>();
            input.add(new ResourceQty(Resource.values()[basicProd[0]], 1));
            input.add(new ResourceQty(Resource.values()[basicProd[1]], 1));
            ArrayList<ResourceQty> output = new ArrayList<>();
            output.add(new ResourceQty(Resource.values()[basicProd[2]], 1));
            Recipe basicRecipe = new Recipe(input, output);
            payload.addProperty("basicProduction", gson.toJson(basicRecipe));
        }
        if(productions.contains(5))
            payload.addProperty("specialProduction1", gson.toJson(Resource.values()[eres0index]));
        if(productions.contains(6))
            payload.addProperty("specialProduction2", gson.toJson(Resource.values()[eres1index]));

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) eres0.getScene().getWindow();
        stage.close();
    }

    private void addProd(int prod){
        if(productions.contains(prod + 1)){
            productions.remove(Integer.valueOf(prod + 1));
            rectangles.get(prod).setVisible(false);
        } else {
            productions.add(prod + 1);
            rectangles.get(prod).setVisible(true);
        }
    }

    public void addbasic(){
        addProd(0);
    }

    public void adds0(){
        addProd(1);
    }

    public void adds1(){
        addProd(2);
    }

    public void adds2(){
        addProd(2);
    }

    public void adde0(){
        addProd(3);
    }

    public void adde1(){
        addProd(4);
    }

    public void changeBasic0(){
        basicProd[0] = (basicProd[0] + 1) % 4;
        basic0.setImage(new Image(getClass().getResourceAsStream("/images/resources/" + basicProd[0] + ".png")));
    }

    public void changeBasic1(){
        basicProd[1] = (basicProd[1] + 1) % 4;
        basic1.setImage(new Image(getClass().getResourceAsStream("/images/resources/" + basicProd[1] + ".png")));
    }

    public void changeBasic2(){
        basicProd[2] = (basicProd[2] + 1) % 4;
        basic2.setImage(new Image(getClass().getResourceAsStream("/images/resources/" + basicProd[2] + ".png")));
    }

    public void changeEres0(){
        eres0index = (eres0index + 1) % 4;
        eres0.setImage(new Image(getClass().getResourceAsStream("/images/resources/" + eres0index + ".png")));
    }

    public void changeEres1() {
        eres1index = (eres1index + 1) % 4;
        eres1.setImage(new Image(getClass().getResourceAsStream("/images/resources/" + eres1index + ".png")));
    }
}
