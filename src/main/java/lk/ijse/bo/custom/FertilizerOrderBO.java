package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PlaceFertilizerOrderDto;

import java.sql.*;

public interface FertilizerOrderBO extends SuperBO {
    String generateNextFertilizerOrderId() throws SQLException;
    boolean saveFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException;
    boolean placeFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException;
}
