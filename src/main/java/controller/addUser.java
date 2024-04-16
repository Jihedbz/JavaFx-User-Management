package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import services.userService;
import javafx.scene.text.Text;
public class addUser {
    userService userService = new userService();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private DatePicker birthday_input;
    @FXML
    private TextField email_input;
    @FXML
    private TextField firstname_input;
    @FXML
    private ComboBox<String> gender_combobox;
    @FXML
    private TextField lastname_input;
    @FXML
    private TextField password_input;
    @FXML
    private TextField username_input;
    @FXML
    private TextField picture_input;
    @FXML
    private TextField phonenumber_input;
    @FXML
    private Text error;
    @FXML
    void reset_input(ActionEvent event) {
        lastname_input.clear();
        birthday_input.setValue(null);
        email_input.clear();
        firstname_input.clear();
        gender_combobox.setValue(null);
        password_input.clear();
        username_input.clear();
        picture_input.clear();
        phonenumber_input.clear();
    }


    @FXML
    void submit_user(ActionEvent event) throws IOException, SQLException {
        if(validateForm()) {
            String picturePath = picture_input.getText();
            Path path = Paths.get(picturePath);
            String fileName = path.getFileName().toString();
            LocalDate selectedDate = birthday_input.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedBirthday = selectedDate.format(formatter);
            String selectedgender = gender_combobox.getValue();
            user user = new user(username_input.getText(), email_input.getText(), password_input.getText(), firstname_input.getText(), lastname_input.getText(),
                    formattedBirthday, selectedgender, fileName, phonenumber_input.getText());
            userService.addUser(user);
            Path destinationPath = Paths.get("src/main/resources/images/Profilepictures", fileName);
            try {
                Files.copy(path, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't copy the file.");
            }
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
        gender_combobox.setItems(genderOptions);
        picture_input.setVisible(false);
    }

    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        mainController.loadFXML("/listUser.fxml");
    }

    public void upload_img(ActionEvent actionEvent) {
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


}
