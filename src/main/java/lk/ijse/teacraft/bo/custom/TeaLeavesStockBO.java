package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaLeavesStockDto;
import lk.ijse.entity.TeaLeavesStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaLeavesStockBO extends SuperBO {
    String generateNextTeaLeavesStockId() throws SQLException;
    boolean saveTeaLeavesStock(TeaLeavesStockDto dto) throws SQLException;
    double getTotalAmount(String teaBookId) throws SQLException;
    List<TeaLeavesStockDto> getAllStockDetails(String dateBookId) throws SQLException;
    TeaLeavesStockDto searchTeaLeavesStock(String text) throws SQLException;
    boolean deleteTeaLeavesStock(String teaStockId) throws SQLException;
    boolean updateTeaLeavesStock(TeaLeavesStockDto teaLeavesStock) throws SQLException;
    double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException;
    boolean updatePayedStatus(String supId) throws SQLException;
    boolean addTeaLeavesStock(TeaLeavesStockDto teaLeavesStockDto, String teaBookId) throws SQLException;
}
