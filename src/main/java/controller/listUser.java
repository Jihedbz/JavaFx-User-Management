package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import entities.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import services.userService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class listUser {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<user, Void> actions;
    @FXML
    private TableView<user> list_user;
    @FXML
    private TableColumn<user, String> user_birthday;
    @FXML
    private TableColumn<user, String> user_email;
    @FXML
    private TableColumn<user, String> user_phonenumber;
    @FXML
    private TableColumn<user, String> user_firstname;
    @FXML
    private TableColumn<user, Integer> user_id;
    @FXML
    private TableColumn<user, String> user_lastname;
    @FXML
    private TableColumn<user, String> user_username;
    @FXML
    private TableColumn<user, String> user_gender;
    @FXML
    private TableColumn<user, String> user_picture;
    @FXML
    private TableColumn<user, Integer> user_level;
    userService userService = new userService();
    //Redirect to Add user
    @FXML
    void add_user(ActionEvent event) throws IOException {
        mainController.loadFXML("/addUser.fxml");
    }
    //Table View initialization
    @FXML
    void initialize() {
        ObservableList<user> list = FXCollections.observableList(userService.getallUserdata());
        user_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        user_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        user_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        user_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        user_birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        user_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        user_level.setCellValueFactory(new PropertyValueFactory<>("level"));
        user_picture.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        URL imageUrl = getClass().getResource("/images/Profilepictures/" + getTableView().getItems().get(getIndex()).getPicture());
                        if (imageUrl != null) {
                            Image image = new Image(imageUrl.toExternalForm());
                            imageView.setImage(image);
                            imageView.setFitWidth(140);
                            imageView.setFitHeight(140);
                            setGraphic(imageView);
                        } else {
                            System.out.println("Image not found: " + item);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        actions.setCellFactory(createActionsCellFactory());
        list_user.setItems(list);
    }

    //Action buttons and their functionality
    private Callback<TableColumn<user, Void>, TableCell<user, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<user, Void>, TableCell<user, Void>>() {
            @Override
            public TableCell<user, Void> call(final TableColumn<user, Void> param) {
                return new TableCell<user, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");
                    {
                        btnUpdate.setOnAction(event -> {
                            user user = getTableView().getItems().get(getIndex());
                            System.out.println(user);
                            try {
                                FXMLLoader loader = mainController.loadFXML("/updateUser.fxml");
                                updateUser updateController = loader.getController();
                                updateController.setUser(user);
                                System.out.println("Selected Personne: " + user);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        btnDelete.setOnAction(event -> {
                            user user = getTableView().getItems().get(getIndex());
                            String image = user.getPicture();
                            showDeleteConfirmation(user);
                            if (image != null && !image.isEmpty()) {
                                Path imagePath = Paths.get("src/main/resources/images/Profilepictures", image);
                                try {
                                    Files.deleteIfExists(imagePath);
                                    System.out.println("Image deleted: " + imagePath);
                                } catch (IOException e) {
                                    System.out.println("Error deleting image: " + e.getMessage());
                                }
                            }
                            refreshlist();
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            user currentUser = getTableView().getItems().get(getIndex());
                            if (currentUser != null) {
                                HBox buttonsBox = new HBox(btnUpdate, btnDelete);
                                setGraphic(buttonsBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        };
    }

    //Table view refresh
    public void refreshlist(){
        ObservableList<user> updatedList = FXCollections.observableList(userService.getallUserdata());
        list_user.setItems(updatedList);
    }
    private void showDeleteConfirmation(user user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete User");
        alert.setContentText("Are you sure you want to delete this user: " + user.getUsername() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.deleteUser(user.getId());
            }
            });
    }
    @FXML
    void display_charts(ActionEvent event) throws IOException {
        mainController.loadFXML("/userCharts.fxml");
    }

}


