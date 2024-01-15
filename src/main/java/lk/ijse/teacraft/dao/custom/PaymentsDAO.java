package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.Payments;

import java.sql.SQLException;
import java.util.List;

public interface PaymentsDAO extends CrudDAO<Payments> {
    List<Payments> getAllPaymentsDetails(String supId) throws SQLException;
}
