package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;


public class adminSideBar {
    @FXML
    public Label welcome;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label level;
    @FXML
    private ImageView picture;
    @FXML
    private Label role;
    @FXML
    private Label username;
    UserSession userSession = UserSession.getInstance();
    @FXML
    void initialize() {
        username.setText(userSession.getUsername());
        role.setText(userSession.getRole());
        String levelstring = String.valueOf(userSession.getLevel());
        level.setText(levelstring);
        URL imageUrl = getClass().getResource("/images/Profilepictures/" + userSession.getPicture());
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            picture.setImage(image);
            picture.setFitWidth(65);
            picture.setFitHeight(65);
        } else {
            System.out.println("Image not found");
        }
    }
    @FXML
    void disconnect(ActionEvent event) throws IOException {
        userSession.cleanUserSession();
        mainController.loadFXML("/login.fxml");
    }

    @FXML
    void goto_blog(ActionEvent event) throws IOException {
        mainController.loadFXML("/login.fxml");

    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {
        mainController.loadFXML("/adminDashboard.fxml");

    }

    @FXML
    void goto_edit(ActionEvent event) throws IOException {
        mainController.loadFXML("/editCurrentuser.fxml");

    }

    @FXML
    void goto_event(ActionEvent event) throws IOException {
        mainController.loadFXML("/login.fxml");

    }

    @FXML
    void goto_forum(ActionEvent event) throws IOException {
        mainController.loadFXML("/login.fxml");

    }

    @FXML
    void goto_shop(ActionEvent event) throws IOException {
        mainController.loadFXML("/login.fxml");

    }

    @FXML
    void goto_user(ActionEvent event) throws IOException {
        mainController.loadFXML("/listUser.fxml");
    }

}
