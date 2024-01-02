package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean saveUser(User dto) throws SQLException {
        return false;
    }

    @Override
    public boolean searchUser(String userName) throws SQLException {
        return false;
    }

    @Override
    public boolean searchEmailAndUsername(String userName, String email) throws SQLException {
        return false;
    }

    @Override
    public boolean updatePassword(String userName, String password) throws SQLException {
        return false;
    }

    @Override
    public boolean searchEmail(String email) throws SQLException {
        return false;
    }

    @Override
    public boolean searchUsernameAndPassword(String userName, String password) throws SQLException {
        return false;
    }
}

