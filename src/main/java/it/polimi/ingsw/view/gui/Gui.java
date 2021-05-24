package it.polimi.ingsw.view.gui;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.controller.DynamicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    private static Scene scene;
    private static GuiManager manager;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("opening"), 1200, 800);
        stage.setTitle("Masters of Renaissance");
        scene.setOnKeyPressed(e -> {
            manager.start();
            scene.setOnKeyPressed(evt -> {});
        });
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/css/master.css").toExternalForm());
        stage.show();
    }

    public static void setManager(GuiManager manager) {
        Gui.manager = manager;
    }

    public static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e){
            System.err.println("IOException - FXML file not found");
        }
    }

    public static void initData(JsonObject data){
        DynamicController controller = fxmlLoader.getController();
        controller.initData(data);
    }

    public static void initData(String property, String value){
        JsonObject data = new JsonObject();
        data.addProperty(property, value);
        DynamicController controller = fxmlLoader.getController();
        controller.initData(data);
    }


    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(Gui.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void send(ResponseMsg responseMsg){
        manager.send(responseMsg);
    }

    public static void close(){
        manager.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}