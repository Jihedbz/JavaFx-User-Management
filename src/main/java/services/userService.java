package services;
import controller.mainController;
import controller.registerUser;
import entities.user;
import interfaces.Iuser;
import utils.databaseConnection;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userService implements Iuser<user> {

    //Add user methode
    @Override
    public void addUser(user user) throws SQLException, IOException {
        if(isEmailTaken(user.getEmail())||isUsernameTaken(user.getUsername())){
            System.out.println("User already exist");
            registerUser registercontroller = mainController.loadFXML("/registerUser.fxml").getController();
            registercontroller.error.setText("Username or email is taken");
            registercontroller.error.setVisible(true);
            return;
        }
        String passwordencrypted = encrypt(user.getPassword());
        String query = "INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, user_birthday, user_gender, user_picture, user_phonenumber, user_level, user_role) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, passwordencrypted);
            preparedStatement.setString(4, user.getFirstname());
            preparedStatement.setString(5, user.getLastname());
            preparedStatement.setString(6, user.getBirthday());
            preparedStatement.setString(7, user.getGender());
            preparedStatement.setString(8, user.getPicture());
            preparedStatement.setString(9, user.getPhonenumber());
            preparedStatement.setInt(10, user.getLevel());
            preparedStatement.setString(11, user.getRole());


            preparedStatement.executeUpdate();
            System.out.println("User added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update User methode
    @Override
    public void updateUser(user user, int id) {
        String passwordencrypted = encrypt(user.getPassword());

        String query = "UPDATE user " +
                "SET user_username = ?, user_email = ?, user_password = ?, user_firstname = ?, user_lastname = ?, user_birthday = ?, " +
                "user_gender = ?, user_picture = ? , user_phonenumber = ?" +
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, passwordencrypted);
            preparedStatement.setString(4, user.getFirstname());
            preparedStatement.setString(5, user.getLastname());
            preparedStatement.setString(6, user.getBirthday());
            preparedStatement.setString(7, user.getGender());
            preparedStatement.setString(8, user.getPicture());
            preparedStatement.setString(8, user.getPicture());
            preparedStatement.setString(9, user.getPhonenumber());

            preparedStatement.setInt(10, id);

            preparedStatement.executeUpdate();
            System.out.println("User updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete User methode
    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with the id = "+ id+" is deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Retrieving data from database
    @Override
    public List<user> getallUserdata() {
        List<user> list = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            Statement srt = databaseConnection.getInstance().getCon().createStatement();
            ResultSet rs = srt.executeQuery(query);
            while(rs.next()){
                user user = new user();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setPassword(rs.getString("user_password"));
                user.setFirstname(rs.getString("user_firstname"));
                user.setLastname(rs.getString("user_lastname"));
                user.setBirthday(rs.getString("user_birthday"));
                user.setGender(rs.getString("user_gender"));
                user.setPicture(rs.getString("user_picture"));
                user.setPhonenumber(rs.getString("user_phonenumber"));
                user.setLevel(rs.getInt("user_level"));
                user.setRole(rs.getString("user_role"));

                list.add(user);
            }
            System.out.println("All users are added to the list!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //Methode to login
    public user loginUser(String Username, String password){
        String query = "SELECT * FROM user WHERE user_username = ? AND user_password = ?";
        String encryptedPassword = encrypt(password);

        try (PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query)) {
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, encryptedPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User login successful, create a user object and return it
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_username");
                    String userEmail = resultSet.getString("user_email");
                    String userPassword = resultSet.getString("user_password");
                    String userFirstname = resultSet.getString("user_firstname");
                    String userLastname = resultSet.getString("user_lastname");
                    String userBirthday = resultSet.getString("user_birthday");
                    String userGender = resultSet.getString("user_gender");
                    String userPicture = resultSet.getString("user_picture");
                    String userPhone = resultSet.getString("user_phonenumber");
                    int userLevel = resultSet.getInt("user_level");
                    String userRole = resultSet.getString("user_role");
                    return new user(userId, userName, userEmail, userPassword, userFirstname, userLastname, userBirthday, userGender, userPicture, userPhone, userLevel, userRole);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // isUsernameTaken/isEmailTaken methodes to check the uniqueness of a user
    private boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE user_username = ?";
        PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
        preparedStatement.setString(1, username);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }
    }
    public boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE user_email = ?";
        PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }
    }

    //Methode to encrypt the Username password
    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateforgottenpassword(String email, String password) {
        String passwordencrypted = encrypt(password);

        String query = "UPDATE user " +
                "SET user_password = ? WHERE user_email = ?";
        try {
            PreparedStatement preparedStatement = databaseConnection.getInstance().getCon().prepareStatement(query);
            preparedStatement.setString(1, passwordencrypted);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            System.out.println("Password updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Add a method to retrieve gender distribution data
    public Map<String, Long> getGenderDistribution() {
        Map<String, Long> genderDistribution = new HashMap<>();
        String query = "SELECT user_gender, COUNT(*) as count FROM user GROUP BY user_gender";

        try {
            Statement statement = databaseConnection.getInstance().getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String gender = resultSet.getString("user_gender");
                long count = resultSet.getLong("count");
                genderDistribution.put(gender, count);
            }

            System.out.println("Gender distribution retrieved!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return genderDistribution;
    }

    public Map<String, Long> getAgeDistribution() {
        Map<String, Long> ageDistribution = new HashMap<>();

        // Example query to get age distribution based on birthdate
        String query = "SELECT FLOOR(DATEDIFF(CURDATE(), user_birthday) / 365) AS age_group, COUNT(*) AS count " +
                "FROM user " +
                "GROUP BY age_group";

        try {
            Statement statement = databaseConnection.getInstance().getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String ageGroup = resultSet.getString("age_group");
                long count = resultSet.getLong("count");
                ageDistribution.put(ageGroup, count);
            }

            System.out.println("Age distribution retrieved!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ageDistribution;
    }
}