package lk.ijse.teacraft.dao.custom.impl;

import lk.ijse.teacraft.dao.custom.CustomerDAO;
import lk.ijse.teacraft.entity.Customer;
import lk.ijse.teacraft.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM customer");
        ArrayList<Customer> allCustomer = new ArrayList<>();
        while (resultSet.next()) {
            Customer entity = new Customer(
                    resultSet.getString("cusId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("mobileNo")
            );
            allCustomer.add(entity);
        }
        return allCustomer;
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)",entity.getCusId(),entity.getFirstName(),entity.getLastName(),entity.getAddress(),entity.getEmail(),entity.getMobileNo());
    }

    @Override
    public boolean update(Customer entity) throws SQLException{
        return SQLUtil.crudUtil("UPDATE customer SET firstName = ? , lastName = ? , address = ?, email = ?, mobileNo = ? WHERE cusId = ?",entity.getFirstName(),entity.getLastName(),entity.getAddress(),entity.getEmail(),entity.getMobileNo(),entity.getCusId());
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.crudUtil("DELETE FROM customer WHERE cusId=?",id);
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1");
        String currentCustomerId = null;
        if (resultSet.next()){
            currentCustomerId = resultSet.getString(1);
            return  splitId(currentCustomerId);
        }
        return splitId(currentCustomerId);
    }

    @Override
    public Customer search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM customer WHERE cusId = ?",id);
        Customer entity= null;
        if (resultSet.next()) {
            entity = new Customer(resultSet.getString("cusId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("mobileNo")
            );
        }
        return entity;
    }

    public String searchCustomerId(String cusNum) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT cusId FROM customer WHERE mobileNo = ?",cusNum);
        resultSet.next();
        return resultSet.getString("cusId");
    }

    private String splitId(String currentCustomerId) {

        if (currentCustomerId!=null){
            String [] split = currentCustomerId.split("C");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9){
                selectedId++;
                return "C00"+selectedId;
            }else if (selectedId<99){
                selectedId++;
                return "C0"+selectedId;
            }else {
                selectedId++;
                return "C"+selectedId;
            }
        }
        return "C001";
    }
}

