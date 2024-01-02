package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.SalaryDto;
import lk.ijse.entity.Salary;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SalaryBO extends SuperBO {
    boolean addSalary(SalaryDto dto) throws SQLException;
    String generateNextSalaryId() throws SQLException;
    List<SalaryDto> getPaymentDetails(String supplierId) throws SQLException;

    boolean saveSalary(SalaryDto dto) throws SQLException;



}
