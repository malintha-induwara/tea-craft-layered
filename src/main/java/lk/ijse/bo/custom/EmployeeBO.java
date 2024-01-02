package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException;
    public String generateNextEmployeeId() throws SQLException;
    public boolean updateEmployee(EmployeeDto employee) throws SQLException;
    EmployeeDto searchEmployee(String empId) throws SQLException;
    List<EmployeeDto> getAllEmployees() throws SQLException;
    boolean deleteEmployee(String empId) throws SQLException;
}
