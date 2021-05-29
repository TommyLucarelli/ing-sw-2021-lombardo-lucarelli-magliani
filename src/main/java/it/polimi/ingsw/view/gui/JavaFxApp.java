package it.polimi.ingsw.view.gui;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.controller.DynamicController;
import it.polimi.ingsw.view.gui.controller.GameBoardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxApp extends Application {
    private static Scene scene;
    private static Gui manager;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("opening"), 1280, 720);
        stage.setTitle("Masters of Renaissance");
        scene.setOnKeyPressed(e -> {
            manager.start();
            scene.setOnKeyPressed(evt -> {});
        });
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/css/master.css").toExternalForm());
        stage.show();
    }

    public static void setManager(Gui manager) {
        JavaFxApp.manager = manager;
    }

    public static Gui getManager(){
        return manager;
    }

    public static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e){
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }
    }

    public static void setRootWithData(String fxml, JsonObject data){
        setRoot(fxml);
        setData(data);
    }

    public static void setData(JsonObject data){
        DynamicController controller = fxmlLoader.getController();
        controller.setData(data);
    }

    public static void setData(String property, String value){
        JsonObject data = new JsonObject();
        data.addProperty(property, value);
        DynamicController controller = fxmlLoader.getController();
        controller.setData(data);
    }


    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void showPopup(String fxml){
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }
        Dialog dialog = new Dialog<>();
        dialog.getDialogPane().setContent(root);

        dialog.show();
    }

    public static void showPopupWithData(String fxml, JsonObject data){
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }
        Dialog dialog = new Dialog<>();
        dialog.getDialogPane().setContent(root);

        DynamicController controller = fxmlLoader.getController();
        controller.setData(data);

        dialog.show();
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