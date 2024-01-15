package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO user VALUES(?,?,?)",
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(User dto) throws SQLException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateID() throws SQLException{
        return null;
    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }

    public boolean searchUser(String userName) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM user WHERE username=?",userName);
        return resultSet.next();
    }

    public boolean searchEmailAndUsername(String userName, String email) throws SQLException{
        ResultSet resultSet =  SQLUtil.crudUtil("SELECT * FROM user WHERE username=? AND email=?",userName,email);
        return  resultSet.next();
    }


    public boolean updatePassword(String userName, String password) throws SQLException {
        ResultSet resultSet =  SQLUtil.crudUtil("SELECT * FROM user WHERE username=?",userName);
        return resultSet.next();
    }


    public boolean searchEmail(String email) throws SQLException {
        ResultSet resultSet =  SQLUtil.crudUtil("SELECT * FROM user WHERE email=?",email);
        return resultSet.next();
    }

    public boolean searchUsernameAndPassword(String userName,String password) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM user WHERE username=? AND password=?",userName,password);
        return resultSet.next();
    }

}

