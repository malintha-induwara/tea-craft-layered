package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.Salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends CrudDAO<Salary> {
    List<Salary> getPaymentDetails(String supplierId) throws SQLException;

}
