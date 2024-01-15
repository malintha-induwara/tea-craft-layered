package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.EmployeeDto;
import lk.ijse.teacraft.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDto dto) throws SQLException;
    String generateNextEmployeeId() throws SQLException;
    boolean updateEmployee(EmployeeDto employee) throws SQLException;
    EmployeeDto searchEmployee(String empId) throws SQLException;
    List<EmployeeDto> getAllEmployees() throws SQLException;
    boolean deleteEmployee(String empId) throws SQLException;
}
