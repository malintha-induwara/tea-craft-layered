package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.entity.Salary;
import lk.ijse.util.SQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public ArrayList<Salary> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Salary entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("INSERT INTO salary VALUES(?,?,?,?,?)",
                entity.getSalaryId(),
                entity.getEmpId(),
                entity.getAmount(),
                entity.getDateCount(),
                Date.valueOf(entity.getDate()));
    }

    @Override
    public boolean update(Salary dto) throws SQLException, ClassNotFoundException {
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
        ResultSet resultSet = SQLUtil.crudUtil("SELECT salaryId FROM salary ORDER BY salaryId DESC LIMIT 1");
        String currentSalaryId = null;
        if (resultSet.next()){
            currentSalaryId = resultSet.getString(1);
            return splitSalaryId(currentSalaryId);
        }
        return splitSalaryId(currentSalaryId);
    }

    private String splitSalaryId(String currentSalaryId) {
        if (currentSalaryId != null){
            String[] split = currentSalaryId.split("S");
            int selectedId = Integer.parseInt(split[1]);

            if(selectedId<9){
                selectedId++;
                return "S00"+selectedId;
            }else if(selectedId<99){
                selectedId++;
                return "S0"+selectedId;
            }else{
                selectedId++;
                return "S"+selectedId;
            }
        }
        return "S001";
    }

    @Override
    public Salary search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public List<Salary> getPaymentDetails(String supplierId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM salary WHERE empId=?", supplierId);
        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()){
            Salary entity = new Salary(resultSet.getString("salaryId"),
                    resultSet.getString("empId"),
                    resultSet.getDouble("amount"),
                    resultSet.getInt("dateCount"),
                    resultSet.getDate("date").toLocalDate());
            salaryList.add(entity);
        }
        return salaryList;
    }


}

