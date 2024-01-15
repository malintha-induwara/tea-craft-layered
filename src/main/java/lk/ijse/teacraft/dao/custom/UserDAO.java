package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;
import lk.ijse.util.SQLUtil;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean searchUser(String userName) throws SQLException;
    boolean searchEmailAndUsername(String userName, String email) throws SQLException;
    boolean updatePassword(String userName, String password) throws SQLException;
    boolean searchEmail(String email) throws SQLException;
    boolean searchUsernameAndPassword(String userName,String password) throws SQLException;

}
