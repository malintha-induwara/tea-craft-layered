package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.SalaryDto;
import lk.ijse.teacraft.entity.Salary;

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
