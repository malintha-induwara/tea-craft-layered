package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public interface TeaOrderDAO extends SuperDAO {
    String generateNextOrderId() throws SQLException;
    boolean saveOrder(String orderId, String cusId, LocalDate date) throws SQLException;
    int getOrderCount(String date) throws SQLException;
}
