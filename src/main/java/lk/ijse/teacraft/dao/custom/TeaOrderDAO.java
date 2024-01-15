package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.SuperDAO;
import lk.ijse.teacraft.entity.TeaOrder;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TeaOrderDAO extends SuperDAO {
    String generateNextOrderId() throws SQLException;
    boolean saveOrder(TeaOrder entity) throws SQLException;
    int getOrderCount(String date) throws SQLException;
}
