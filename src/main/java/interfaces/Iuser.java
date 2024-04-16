package interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Iuser <T>{
    void addUser(T t) throws SQLException, IOException;
    void updateUser(T t, int id);
    void deleteUser(int id);
    List<T> getallUserdata();
}
