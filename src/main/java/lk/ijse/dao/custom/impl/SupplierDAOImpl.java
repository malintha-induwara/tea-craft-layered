package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_supplier");
        ArrayList<Supplier> dtoList = new ArrayList<>();
        while (resultSet.next()){
            Supplier dto = new Supplier(resultSet.getString("supId"),
            resultSet.getString("firstName"),
            resultSet.getString("lastName"),
            resultSet.getString("address"),
            resultSet.getString("bank"),
            resultSet.getString("bankNo"),
            resultSet.getString("mobilNo")
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("INSERT INTO tea_supplier VALUES(?, ?, ?, ?, ?, ?, ?)",entity.getSupId(),entity.getFirstName(),entity.getLastName(),entity.getAddress(),entity.getBank(),entity.getBankNo(),entity.getMobileNo());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("UPDATE tea_supplier SET firstName=?,lastName=?,address=?,bank=?,bankNo=?,mobileNo=? WHERE supId=?",entity.getFirstName(),entity.getLastName(),entity.getAddress(),entity.getBank(),entity.getBankNo(),entity.getMobileNo(),entity.getSupId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("DELETE FROM tea_supplier WHERE supId = ?",id);
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT supId FROM tea_supplier ORDER BY supId DESC LIMIT 1");
        String currentSupplierId = null;

        if (resultSet.next()){
            currentSupplierId = resultSet.getString(1);
            return splitSupplierId(currentSupplierId);
        }

        return splitSupplierId(currentSupplierId);
    }

    private String splitSupplierId(String currentSupplierId) {
        if (currentSupplierId!=null){
            String [] split = currentSupplierId.split("S");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9) {
                selectedId++;
                return "S00" + selectedId;
            }else if (selectedId<99) {
                selectedId++;
                return "S0" + selectedId;
            }else {
                selectedId++;
                return "S" + selectedId;
            }
        }
        return "S001";
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_supplier WHERE supId = ?",id);
        Supplier entity = null;
        if (resultSet.next()){
            entity=new Supplier(resultSet.getString("supId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("address"),
                    resultSet.getString("bank"),
                    resultSet.getString("bankNo"),
                    resultSet.getString("mobileNo")
                    );
        }
        return entity;
    }

    @Override
    public String getSupplierName(String supId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT firstName FROM tea_supplier WHERE supId = ?",supId);
        String name = null;
        if (resultSet.next()) {
            name = resultSet.getString(1);
        }
        return name;
    }
}

