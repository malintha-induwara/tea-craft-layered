package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet resultSet=SQLUtil.crudUtil("SELECT * FROM employee");
        ArrayList<Employee> allEmployee = new ArrayList<>();
        while (resultSet.next()){
            Employee dto = new Employee(resultSet.getString("empId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("address"),
                    resultSet.getString("sex"),
                    resultSet.getString("dob"),
                    resultSet.getString("mobileNo"));
            allEmployee.add(dto);
        }
        return allEmployee;
    }

    @Override
    public boolean save(Employee dto) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?)",dto.getEmpId(),dto.getFirstName(),dto.getLastName(),dto.getAddress(),dto.getSex(),dto.getDateOfBirth(),dto.getMobileNo());
    }

    @Override
    public boolean update(Employee dto) throws SQLException {
        return SQLUtil.crudUtil("UPDATE employee SET firstName = ? , lastName = ? , address = ? , sex = ? , dob = ? , mobileNo = ? WHERE empId = ?",dto.getFirstName(),dto.getLastName(),dto.getAddress(),dto.getSex(),dto.getMobileNo(),dto.getEmpId());
    }

    @Override
    public boolean exist(String id) throws SQLException{
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return SQLUtil.crudUtil("DELETE FROM employee WHERE empId = ?",id);
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1");

        String currentEmployeeId = null;
        if (resultSet.next()){
            currentEmployeeId = resultSet.getString(1);
            return splitEmployeeId(currentEmployeeId);
        }
        return splitEmployeeId(currentEmployeeId);
    }

    @Override
    public Employee search(String id) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM employee WHERE empId = ?",id);
        Employee entity= null;
        if (resultSet.next()){
            entity = new Employee(resultSet.getString("empId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("address"),
                    resultSet.getString("sex"),
                    resultSet.getString("dob"),
                    resultSet.getString("mobileNo")
            );
        }
        return entity;
    }

    private String splitEmployeeId(String currentEmployeeId) {
        if (currentEmployeeId!=null){
            String [] split = currentEmployeeId.split("E");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9){
                selectedId++;
                return "E00"+selectedId;
            }else if (selectedId<99){
                selectedId++;
                return "E0"+selectedId;
            }else {
                selectedId++;
                return "E"+selectedId;
            }
        }
        return "E001";
    }
}

