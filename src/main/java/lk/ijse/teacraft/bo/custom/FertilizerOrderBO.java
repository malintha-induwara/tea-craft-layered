package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.dto.PlaceFertilizerOrderDto;

import java.sql.*;

public interface FertilizerOrderBO extends SuperBO {
    String generateNextFertilizerOrderId() throws SQLException;
    boolean saveFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException;
    boolean placeFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException;
}
