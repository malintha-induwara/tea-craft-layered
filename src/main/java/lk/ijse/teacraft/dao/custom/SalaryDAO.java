package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends CrudDAO<Salary> {
    List<Salary> getPaymentDetails(String supplierId) throws SQLException;

}
