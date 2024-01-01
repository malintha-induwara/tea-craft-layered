package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {


    public boolean saveEmployee(Employee dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpId());
        pstm.setString(2, dto.getFirstName());
        pstm.setString(3, dto.getLastName());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getSex());
        pstm.setString(6, dto.getDateOfBirth());
        pstm.setString(7, dto.getMobileNo());


        boolean isSaved = pstm.executeUpdate()>0;

        return isSaved;
    }

    public String generateNextEmployeeId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT empId FROM employee ORDER BY empId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        String currentEmployeeId = null;

        if (resultSet.next()){
            currentEmployeeId = resultSet.getString(1);
            return splitEmployeeId(currentEmployeeId);
        }

        return splitEmployeeId(currentEmployeeId);
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

    public boolean updateEmployee(Employee employee) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET firstName = ? , lastName = ? , address = ? , sex = ? , dob = ? , mobileNo = ? WHERE empId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, employee.getFirstName());
        pstm.setString(2, employee.getLastName());
        pstm.setString(3, employee.getAddress());
        pstm.setString(4, employee.getSex());
        pstm.setString(5, employee.getDateOfBirth());
        pstm.setString(6, employee.getMobileNo());
        pstm.setString(7, employee.getEmpId());


        return pstm.executeUpdate()>0;
    }

    public Employee searchEmployee(String empId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE empId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, empId);

        ResultSet resultSet = pstm.executeQuery();

        Employee dto = null;

        if (resultSet.next()){
            String emp_Id = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String address = resultSet.getString(4);
            String sex = resultSet.getString(5);
            String dateOfBirth = resultSet.getString(6);
            String mobileNo = resultSet.getString(7);

            dto = new Employee(emp_Id,firstName,lastName,address,sex,dateOfBirth,mobileNo);

        }

        return dto;
    }

    public List<Employee> getAllEmployees() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<Employee> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){

            String empId = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String address = resultSet.getString(4);
            String sex = resultSet.getString(5);
            String dateOfBirth = resultSet.getString(6);
            String mobileNo = resultSet.getString(7);

            Employee dto = new Employee(empId,firstName,lastName,address,sex,dateOfBirth,mobileNo);
            dtoList.add(dto);


        }
            return dtoList;
    }

    public boolean deleteEmployee(String empId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE empId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,empId);

        return pstm.executeUpdate()>0;

    }
}
