package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.dto.PlaceTeaOrderDto;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TeaOrderBO extends SuperBO {
    String generateNextOrderId() throws SQLException;
    int getOrderCount(String date) throws SQLException;
    boolean placeOrder(PlaceTeaOrderDto dto) throws SQLException;
}
