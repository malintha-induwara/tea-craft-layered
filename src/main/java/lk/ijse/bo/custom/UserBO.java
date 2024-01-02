package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserBO extends SuperBO {

    boolean saveUser(User dto) throws SQLException, ClassNotFoundException;
    boolean searchUser(String userName) throws SQLException, ClassNotFoundException;
    public boolean searchEmailAndUsername(String userName, String email) throws SQLException, ClassNotFoundException;
    public boolean updatePassword(String userName, String password) throws SQLException, ClassNotFoundException;
    public boolean searchEmail(String email) throws SQLException, ClassNotFoundException;
    public boolean searchUsernameAndPassword(String userName, String password) throws SQLException, ClassNotFoundException;
}
