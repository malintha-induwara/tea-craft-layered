package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.TeaLeavesStock;

import java.sql.SQLException;
import java.util.List;

public interface TeaLeavesStockDAO extends CrudDAO<TeaLeavesStock> {
    boolean updatePayedStatus(String supId) throws SQLException;
    double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException;
    List<TeaLeavesStock> getAllStockDetails(String dateBookId) throws SQLException;
    double getTotalAmount(String teaBookId) throws SQLException;
}
