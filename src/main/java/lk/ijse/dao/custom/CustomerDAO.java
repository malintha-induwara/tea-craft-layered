package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Customer;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    String searchCustomerId(String cusNum) throws SQLException, ClassNotFoundException;
}
