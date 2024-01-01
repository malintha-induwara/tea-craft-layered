package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;
import lk.ijse.util.SQLUtil;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public boolean searchUser(String userName) throws SQLException, ClassNotFoundException;
    public boolean searchEmailAndUsername(String userName, String email) throws SQLException, ClassNotFoundException;
    public boolean updatePassword(String userName, String password) throws SQLException, ClassNotFoundException;
    public boolean searchEmail(String email) throws SQLException, ClassNotFoundException ;
    public boolean searchUsernameAndPassword(String userName,String password) throws SQLException, ClassNotFoundException;

}
