package controller;

import entities.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.userService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editCurrentUser {

    @FXML
    private DatePicker birthday_input;

    @FXML
    private TextField email_input;

    @FXML
    private Text error;

    @FXML
    private TextField firstname_input;

    @FXML
    private ComboBox<String> gender_combobox;

    @FXML
    private ImageView image_view;

    @FXML
    private TextField lastname_input;

    @FXML
    private TextField password_input;

    @FXML
    private TextField phonenumber_input;

    @FXML
    private TextField picture_input;

    @FXML
    private TextField username_input;
    UserSession userSession = UserSession.getInstance();
    userService userService = new userService();

    @FXML
    void back_to_list(ActionEvent event) {

    }

    @FXML
    void reset_input(ActionEvent event) {

    }

    @FXML
    void submit_user(ActionEvent event) {
        if(validateForm()) {
            String picturePath = picture_input.getText();

            Path path = Paths.get(picturePath);
            String fileName = path.getFileName().toString();
            LocalDate selectedDate = birthday_input.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedBirthday = selectedDate.format(formatter);
            String selectedgender = gender_combobox.getValue();
            user user1 = new user(userSession.getId(), username_input.getText(), email_input.getText(), password_input.getText(), firstname_input.getText(), lastname_input.getText(),
                    formattedBirthday, selectedgender, fileName, phonenumber_input.getText());
            showUpdateConfirmation(user1);
            //userService.updateUser(user1, user1.getId());
            Path destinationPath = Paths.get("src/main/resources/images/Profilepictures", fileName);
            try {
                Files.copy(path, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload your profile picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName().toLowerCase();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                picture_input.setText(selectedFile.getPath());
            } else {
                System.out.println("Invalid file format. Please select a PNG or JPG file.");
            }
        } else {
            System.out.println("No file selected");
        }
    }
    @FXML
    void initialize(){
        firstname_input.setText(userSession.getFirstname());
        lastname_input.setText(userSession.getLastname());
        phonenumber_input.setText(userSession.getPhonenumber());
        username_input.setText(userSession.getUsername());
        email_input.setText(userSession.getEmail());
        password_input.setText(userSession.getPassword());
        picture_input.setText(userSession.getPicture());
        String birthdayString = userSession.getBirthday();
        LocalDate date = LocalDate.parse(birthdayString);
        birthday_input.setValue(date);
        System.out.println(picture_input.getText());
        URL imageUrl = getClass().getResource("/images/Profilepictures/" + picture_input.getText());
        System.out.println(imageUrl);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            image_view.setImage(image);
            image_view.setFitWidth(200);
            image_view.setFitHeight(200);
        } else {
            System.out.println("Image not found");
        }
        ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
        gender_combobox.setItems(genderOptions);
        picture_input.setVisible(false);
        error.setVisible(false);
    }

    private boolean validateForm() {
        if (username_input.getText().isEmpty() || email_input.getText().isEmpty() || password_input.getText().isEmpty() ||
                firstname_input.getText().isEmpty() || lastname_input.getText().isEmpty() ||
                birthday_input.getValue() == null || gender_combobox.getValue() == null || picture_input.getText().isEmpty()) {
            error.setText("All fields must be filled");
            error.setVisible(true);
            return false;
        }

        String email = email_input.getText();
        if (!isEmailValid(email)) {
            error.setText("Invalid email address");
            error.setVisible(true);
            return false;
        }

        if (!isValidPassword(password_input.getText())) {
            error.setText("Password must contain at least one uppercase letter, one number, and one special character");
            error.setVisible(true);
            return false;
        }
        return true;
    }

    //Password validation methode
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!/])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //Email validation methode
    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void showUpdateConfirmation(user user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Update User");
        alert.setContentText("Are you sure you want to update this user: " + user.getUsername() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.updateUser(user, user.getId());
            }
        });
    }

}
