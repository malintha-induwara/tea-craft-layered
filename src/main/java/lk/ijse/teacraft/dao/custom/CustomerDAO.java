package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.Customer;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    String searchCustomerId(String cusNum) throws SQLException;
}
