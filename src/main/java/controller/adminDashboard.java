    package controller;

    import java.net.URL;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Comparator;
    import java.util.Map;
    import java.util.ResourceBundle;
    import entities.user;
    import javafx.animation.*;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.geometry.Pos;
    import javafx.scene.control.Label;
    import javafx.scene.control.ListCell;
    import javafx.scene.control.ListView;
    import javafx.scene.control.ProgressBar;
    import javafx.scene.layout.*;
    import javafx.scene.shape.Rectangle;
    import javafx.scene.text.Text;
    import javafx.util.Duration;
    import services.userService;
    import javafx.scene.chart.BarChart;
    import javafx.scene.chart.CategoryAxis;
    import javafx.scene.chart.NumberAxis;
    import javafx.scene.chart.XYChart;
    import javafx.scene.chart.PieChart;

    public class adminDashboard {
        @FXML
        private ResourceBundle resources;
        @FXML
        private URL location;
        @FXML
        private BarChart<String, Number> age_barchart;
        @FXML
        private Text date;
        @FXML
        private PieChart gender_piechart;
        @FXML
        private Pane ranking_pane;
        @FXML
        private VBox ranking_box;
        @FXML
        private Text totalusers;
        @FXML
        private Text scrolling_text;
        @FXML
        private Pane news_pane;
        @FXML
        private Text time;
        UserSession userSession = UserSession.getInstance();




        @FXML
        void initialize() {
            updateTime();

            userService userService = new userService();
            ObservableList<user> userList = FXCollections.observableList(userService.getallUserdata());
            animateScrollingText();
            animateTotalUsers(userList.size());
            userList.sort(Comparator.comparingInt(user::getLevel).reversed());
            Label titleLabel = new Label("User Ranking:");
            titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white;");
            ListView<user> listView = new ListView<>(userList);
            listView.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 20;");
            listView.setCellFactory(param -> new RankingListCell());
            VBox vBox = new VBox(10);
            vBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 20;");
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(titleLabel, listView);
            vBox.setAlignment(Pos.CENTER);
            ranking_box.getChildren().setAll(vBox);

            Map<String, Long> genderDistribution = userService.getGenderDistribution();

            PieChart.Data[] pieChartData = genderDistribution.entrySet().stream()
                    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                    .toArray(PieChart.Data[]::new);

            gender_piechart.getData().addAll(pieChartData);
            Map<String, Long> ageDistribution = userService.getAgeDistribution();

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            ageDistribution.entrySet().forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));

            age_barchart.getData().add(series);
        }

        static class RankingListCell extends ListCell<user> {
            @Override
            protected void updateItem(user user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    StackPane stackPane = new StackPane();
                    stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 20;");
                    Label usernameLabel = new Label(user.getUsername());
                    ProgressBar progressBar = new ProgressBar(user.getLevel() / 10.0);
                    Label levelLabel = new Label("Level: " + user.getLevel());

                    stackPane.getChildren().addAll(usernameLabel, progressBar, levelLabel);
                    StackPane.setAlignment(usernameLabel, Pos.CENTER_LEFT);
                    StackPane.setAlignment(progressBar, Pos.CENTER);
                    StackPane.setAlignment(levelLabel, Pos.CENTER_RIGHT);
                    setGraphic(stackPane);
                }
            }
        }

        private void animateTotalUsers(int totalUsers) {
            int animationDurationMillis = 2000;
            int initialCount = 1;

            Timeline timeline = new Timeline();

            totalusers.setText(String.valueOf(initialCount));

            for (int count = initialCount + 1; count <= totalUsers; count++) {
                KeyValue keyValue = new KeyValue(totalusers.textProperty(), String.valueOf(count), Interpolator.LINEAR);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDurationMillis * (count - initialCount) / (totalUsers - initialCount)), keyValue);
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();
        }

        private void animateScrollingText() {
            String newsText = "° Welcome back Mr: " + userSession.getFirstname() + " " + userSession.getLastname() + " Here's what happened while you were out! °";
            scrolling_text.setText(newsText);
            Rectangle clip = new Rectangle(1027, 60);
            news_pane.setClip(clip);
            news_pane.layout();
            double textWidth = scrolling_text.getLayoutBounds().getWidth();
            int animationDurationMillis = (int) (textWidth * 20);
            TranslateTransition transitionOut = new TranslateTransition(Duration.millis(animationDurationMillis), scrolling_text);
            transitionOut.setByX(-textWidth);
            TranslateTransition transitionIn = new TranslateTransition(Duration.ZERO, scrolling_text);
            transitionIn.setByX(1027);
            SequentialTransition sequentialTransition = new SequentialTransition(transitionOut, transitionIn);
            sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);
            sequentialTransition.play();
        }
        private void updateTime() {
            // Get the current time
            LocalTime currentTime = LocalTime.now();

            // Format the time using a DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = currentTime.format(formatter);

            LocalDate currentDate = LocalDate.now();

            // Format the date using a DateTimeFormatter
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter1);
            // Set the formatted time to the time Text element
            date.setText(formattedDate+"  |  "+formattedTime);
        }


    }





