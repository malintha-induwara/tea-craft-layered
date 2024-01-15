package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDto dto) throws SQLException;
    boolean searchUser(String userName) throws SQLException;
    boolean searchEmailAndUsername(String userName, String email) throws SQLException;
    boolean updatePassword(String userName, String password) throws SQLException;
    boolean searchEmail(String email) throws SQLException;
    boolean searchUsernameAndPassword(String userName, String password) throws SQLException;
}