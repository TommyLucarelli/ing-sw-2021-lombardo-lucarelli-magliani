package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class WarehousePlacementController implements DynamicController {
    @FXML
    ImageView w0, w1, w2, w3, w4, w5, w6, w7, w8, w9, m0, m1, m2, m3, s0, s1;

    @FXML
    Rectangle r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, rm0, rm1, rm2, rm3;

    @FXML
    Button confirmButton;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Text specialAbilityText, specialAbilityText2;

    ArrayList<Resource> warehouse;
    ArrayList<Resource> resources;
    ArrayList<ImageView> warehouseImages = new ArrayList<>();
    ArrayList<ImageView> resourceImages = new ArrayList<>();
    ArrayList<ImageView> allImages = new ArrayList<>();
    ArrayList<Rectangle> rectangles = new ArrayList<>();

    int warehouseSize = 6;
    boolean[] canBeRemoved = new boolean[10];
    Resource activeResource = Resource.ANY;
    int activeResourceIndex;
    boolean alreadyStored = false;

    @Override
    public void setData(JsonObject data) {
        w6.setVisible(false);
        w7.setVisible(false);
        w8.setVisible(false);
        w9.setVisible(false);

        if(JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[0] != 0){
            specialAbilityText.setVisible(false);
            s0.setImage(new Image(getClass().getResourceAsStream("/images/warehouse/extra" + (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[0] - 53) + ".png")));
            warehouseSize += 2;
            w6.setVisible(true);
            w7.setVisible(true);
            if(JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[1] != 0) {
                s0.setImage(new Image(getClass().getResourceAsStream("/images/warehouse/extra" + (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[1] - 53) + ".png")));
                warehouseSize += 2;
                w8.setVisible(true);
                w9.setVisible(true);
            } else {
                specialAbilityText2.setVisible(true);
            }
        } else {
            specialAbilityText2.setVisible(false);
            specialAbilityText.setVisible(true);
        }
        Arrays.fill(canBeRemoved, true);
        warehouseImages.addAll(Arrays.asList(w0, w1, w2, w3, w4, w5, w6, w7, w8, w9));
        resourceImages.addAll(Arrays.asList(m0, m1, m2, m3));
        allImages.addAll(Arrays.asList(w0, w1, w2, w3, w4, w5, w6, w7, w8, w9, m0, m1, m2, m3));
        rectangles.addAll(Arrays.asList(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, rm0, rm1, rm2, rm3));

        for(Rectangle rect: rectangles) rect.setVisible(false);

        Gson gson = new Gson();
        String json = data.get("warehouse").getAsString();
        warehouse = gson.fromJson(json, new TypeToken<ArrayList<Resource>>(){}.getType());

        json = data.get("resourcesArray").getAsString();
        resources = gson.fromJson(json, new TypeToken<ArrayList<Resource>>(){}.getType());

        for (int i = 0; i < 10; i++) {
            if(warehouse.get(i) != Resource.ANY){
                warehouseImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(i).ordinal() + ".png")));
                canBeRemoved[i] = false;
            }
        }

        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i) != Resource.ANY) {
                resourceImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + resources.get(i).ordinal() + ".png")));
            }
        }
    }

    public void confirmAction(){
        int discarded = 0;
        for (int i = 0; i < resources.size(); i++) {
            if(resources.get(i) != Resource.ANY) discarded++;
        }

        Gson gson = new Gson();
        String json = gson.toJson(warehouse);

        JsonObject payload = new JsonObject();
        payload.addProperty("discarded", discarded);
        payload.addProperty("placed", json);
        payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) w0.getScene().getWindow();
        stage.close();
    }

    public void click0() {
        placementMethod(0);
    }

    public void click1() {
        placementMethod(1);
    }

    public void click2() {
        placementMethod(2);
    }

    public void click3() {
        placementMethod(3);
    }

    public void click4() {
        placementMethod(4);
    }

    public void click5() {
        placementMethod(5);
    }

    public void click6() {
        placementMethod(6);
    }

    public void click7() {
        placementMethod(7);
    }

    public void click8() {
        placementMethod(8);
    }

    public void click9() {
        placementMethod(9);
    }

    public void clickm0() {
        placementMethod(10);
    }

    public void clickm1() {
        placementMethod(11);
    }

    public void clickm2() {
        placementMethod(12);
    }

    public void clickm3() {
        placementMethod(13);
    }

    private void placementMethod(int index){
        if(activeResource == Resource.ANY){ //SELECTING
            activeResourceIndex = index;
            if(index < 10){
                activeResource = warehouse.get(index);
                alreadyStored = true;
            }
            else activeResource = resources.get(index - 10);

            rectangles.get(index).setStroke(Color.BLUE);
            rectangles.get(index).setVisible(true);

            for (int i = 0; i < warehouseSize; i++) {
                if(alreadyStored || canBeRemoved[i]){
                    rectangles.get(i).setStroke(Color.YELLOW);
                    rectangles.get(i).setVisible(true);
                }
            }
        } else { //PLACEMENT
            //System.out.println("placement - alreadystored: " + alreadyStored + "; index: " + index + "; canBeRemoved: " + canBeRemoved[index]);
            if(alreadyStored && index < warehouseSize) swapResources(activeResourceIndex, index);
            else if(index < warehouseSize && canBeRemoved[index]){
                if(warehouse.get(index) != Resource.ANY)
                    addResourceWithSwap(activeResourceIndex, index);
                else
                    addResource(activeResourceIndex, index);
            }

            activeResource = Resource.ANY;
            for (Rectangle rect: rectangles) {
                rect.setVisible(false);
            }
        }
    }

    private void swapResources(int index1, int index2){
        boolean c = canBeRemoved[index1];
        canBeRemoved[index1] = canBeRemoved[index2];
        canBeRemoved[index2] = c;

        warehouse.set(index1, warehouse.get(index2));
        warehouse.set(index2, activeResource);

        warehouseImages.get(index1).setImage((warehouse.get(index1) != Resource.ANY) ?
                new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(index1).ordinal() + ".png")) :
                null);

        warehouseImages.get(index2).setImage((warehouse.get(index2) != Resource.ANY) ?
                new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(index2).ordinal() + ".png")) :
                null);

        alreadyStored = false;
    }

    private void addResource(int index1, int index2){
        warehouse.set(index2, activeResource);

        resourceImages.get(index1 - 10).setImage(null);
        resourceImages.get(index1 - 10).setDisable(true);
        resources.set(index1 - 10, Resource.ANY);
        System.gc();
        warehouseImages.get(index2).setImage((warehouse.get(index2) != Resource.ANY) ?
                new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(index2).ordinal() + ".png")) :
                null);

        alreadyStored = false;
    }

    private void addResourceWithSwap(int index1, int index2){
        resourceImages.get(index1 - 10).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(index2).ordinal() + ".png")));

        resources.set(index1 - 10, warehouse.get(index2));
        warehouse.set(index2, activeResource);


        warehouseImages.get(index2).setImage((warehouse.get(index2) != Resource.ANY) ?
                new Image(getClass().getResourceAsStream("/images/resources/" + warehouse.get(index2).ordinal() + ".png")) :
                null);

        alreadyStored = false;
    }
}
