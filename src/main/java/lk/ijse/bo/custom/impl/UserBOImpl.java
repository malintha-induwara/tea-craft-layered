package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

   public static String userName;
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDto dto) throws SQLException{
        return userDAO.save(new User(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail()
        ));
    }

    @Override
    public boolean searchUser(String userName) throws SQLException {
        return userDAO.searchUser(userName);
    }

    @Override
    public boolean searchEmailAndUsername(String userName, String email) throws SQLException {
        return userDAO.searchEmailAndUsername(userName,email);
    }

    @Override
    public boolean updatePassword(String userName, String password) throws SQLException {
        return userDAO.updatePassword(userName,password);
    }

    @Override
    public boolean searchEmail(String email) throws SQLException {
        return userDAO.searchEmail(email);
    }

    @Override
    public boolean searchUsernameAndPassword(String userName, String password) throws SQLException {
        return userDAO.searchUsernameAndPassword(userName,password);
    }
}

