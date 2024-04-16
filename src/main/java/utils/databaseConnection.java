package utils;
import java.sql.*;
public class databaseConnection {
    private final String url="jdbc:mysql://localhost:3306/techterra";
    private final String login="root";
    private final String password="";
    public static databaseConnection instance;
    Connection con;
    private databaseConnection(){
        try {
            con = DriverManager.getConnection(url, login, password);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getCon() {return con;}
    public static databaseConnection getInstance() {
        if(instance == null){
            instance = new databaseConnection();
        }
        return instance;
    }
}
