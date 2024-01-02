package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Payments;

import java.sql.SQLException;
import java.util.List;

public interface PaymentsDAO extends CrudDAO<Payments> {
    List<Payments> getAllPaymentsDetails(String supId) throws SQLException;
}
