package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PlaceTeaOrderDto;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TeaOrderBO extends SuperBO {
    String generateNextOrderId() throws SQLException;
    boolean saveOrder(String orderId, String cusId, LocalDate date) throws SQLException;
    int getOrderCount(String date) throws SQLException;
    boolean placeOrder(PlaceTeaOrderDto dto) throws SQLException;
}
