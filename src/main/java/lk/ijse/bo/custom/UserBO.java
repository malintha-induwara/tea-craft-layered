package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserBO extends SuperBO {
    String userName = null;
    boolean saveUser(User dto) throws SQLException ;
    boolean searchUser(String userName) throws SQLException;
    public boolean searchEmailAndUsername(String userName, String email) throws SQLException;
    public boolean updatePassword(String userName, String password) throws SQLException;
    public boolean searchEmail(String email) throws SQLException;
    public boolean searchUsernameAndPassword(String userName, String password) throws SQLException;
}
