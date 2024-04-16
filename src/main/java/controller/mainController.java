package controller;
import entities.user;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class mainController extends Application {
    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException{
        mainController.primaryStage = primaryStage;
        Image favicon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/favicon.png")));
        primaryStage.setTitle("TechTerra Portal");
        primaryStage.getIcons().add(favicon);
        loadFXML("/login.fxml");
    }
    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(mainController.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return loader;
    }
}
