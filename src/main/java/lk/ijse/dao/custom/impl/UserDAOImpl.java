package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.util.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("INSERT INTO user VALUES(?,?,?)",
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean searchUser(String userName) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("SELECT * FROM user WHERE username=?",userName);
    }

    public boolean searchEmailAndUsername(String userName, String email) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("SELECT * FROM user WHERE username=? AND email=?",userName,email);
    }


    public boolean updatePassword(String userName, String password) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("UPDATE user SET password=? WHERE username=?",password,userName);
    }


    public boolean searchEmail(String email) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("SELECT * FROM user WHERE email=?",email);
    }

    public boolean searchUsernameAndPassword(String userName,String password) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("SELECT * FROM user WHERE username=? AND password=?",userName,password);
    }

}

