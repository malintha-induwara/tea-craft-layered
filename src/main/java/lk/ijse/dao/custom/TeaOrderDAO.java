package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.TeaOrder;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TeaOrderDAO extends SuperDAO {
    String generateNextOrderId() throws SQLException;
    boolean saveOrder(TeaOrder entity) throws SQLException;
    int getOrderCount(String date) throws SQLException;
}
