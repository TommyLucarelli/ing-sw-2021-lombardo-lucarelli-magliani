package it.polimi.ingsw.view.gui;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.controller.DynamicController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Entry point for the JavaFX application.
 */
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

    /**
     * Setter method: sets the Gui manager.
     * @param manager the Gui manager class' instance.
     */
    public static void setManager(Gui manager) {
        JavaFxApp.manager = manager;
    }

    /**
     * Getter method.
     * @return the application's manager.
     */
    public static Gui getManager(){
        return manager;
    }

    /**
     * Sets the root of the scene.
     * @param fxml the name of the fxml file to set the scene.
     */
    public static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e){
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }
    }

    /**
     * Passes data to the active scene via the DynamicController interface.
     * @param data the JsonObject containing the data to be passed to the scene.
     */
    public static void setData(JsonObject data){
        DynamicController controller = fxmlLoader.getController();
        controller.setData(data);
    }

    /**
     * Passes a single property as JSON ({"property": "value"}) to the active scene via the DynamicController interface.
     * @param property the name of the property.
     * @param value the value of the property.
     */
    public static void setData(String property, String value){
        JsonObject data = new JsonObject();
        data.addProperty(property, value);
        DynamicController controller = fxmlLoader.getController();
        controller.setData(data);
    }

    /**
     * Combination of setRoot and setData.
     * @param fxml the name of the fxml file to set the scene.
     * @param data the JsonObject containing the data to be passed to the scene.
     */
    public static void setRootWithData(String fxml, JsonObject data){
        setRoot(fxml);
        setData(data);
    }

    /**
     * Loads the specified fxml file into the fxmlLoader.
     * @param fxml the fxml filename.
     * @return the Parent object loaded by the loader.
     * @throws IOException if the specified file does not exists.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Shows a popup in front of the scene.
     * @param fxml the filename of the popup scene fxml.
     */
    public static void showPopup(String fxml){
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }

        Parent finalRoot = root;
        new Thread(() -> Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(finalRoot);

            dialog.setOnCloseRequest(Event::consume);

            dialog.setScene(scene);
            dialog.showAndWait();
        })).start();
    }

    /**
     * Combination of showPopup and setData.
     * @param fxml the filename of the popup scene fxml.
     * @param data the JsonObject containing the data to be passed to the scene.
     */
    public static void showPopupWithData(String fxml, JsonObject data){
        fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("/fxml/" + fxml + ".fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("IOException - FXML file not found: " + fxml + ".fxml");
        }

        Parent finalRoot = root;
        new Thread(() -> Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(finalRoot);

            DynamicController controller = fxmlLoader.getController();
            controller.setData(data);

            dialog.setOnCloseRequest(Event::consume);
            dialog.setScene(scene);
            dialog.showAndWait();
        })).start();
    }

    /**
     * Sends a message to the server.
     * @param responseMsg the ResponseMsg to be sent.
     */
    public static void send(ResponseMsg responseMsg){
        manager.send(responseMsg);
    }

    /**
     * Closes the application and the connection to the server.
     */
    public static void close(){
        manager.close();
    }

    /**
     * Launches the application.
     * @param args args passed to main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}