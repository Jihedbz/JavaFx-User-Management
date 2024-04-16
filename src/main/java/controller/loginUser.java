package controller;
import entities.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.userService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class loginUser {

    @FXML
    private TextField password_input;
    @FXML
    private TextField username_input;
    @FXML
    private Text error;
    private userService userService = new userService();

    @FXML
    void login(ActionEvent event) throws SQLException {
        try {
            user user;
            if ((user = userService.loginUser(username_input.getText(), password_input.getText()))!=null) {
                UserSession.getInstance(user.getId(),user.getUsername(),user.getEmail(), user.getPassword(),user.getFirstname(),user.getLastname(),user.getBirthday(),user.getGender(),user.getPicture(),user.getPhonenumber(),user.getLevel(),user.getRole());
                if(Objects.equals(user.getRole(), "Admin"))
                    mainController.loadFXML("/adminDashboard.fxml");
            } else {
                error.setText("Your username or password are incorrect");
                error.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            error.setVisible(true);
        }
    }
    @FXML
    void register_page(ActionEvent event) throws IOException {
        mainController.loadFXML("/registerUser.fxml");
    }
    @FXML
    void initialize() {
        error.setVisible(false);
    }
    @FXML
    void redirect_passwordpage(ActionEvent event) throws IOException {
        mainController.loadFXML("/forgotPassword.fxml");
    }
}
