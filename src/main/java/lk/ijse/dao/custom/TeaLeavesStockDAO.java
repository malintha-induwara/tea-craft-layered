package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.TeaLeavesStock;

import java.sql.SQLException;
import java.util.List;

public interface TeaLeavesStockDAO extends CrudDAO<TeaLeavesStock> {
    boolean updatePayedStatus(String supId) throws SQLException;
    double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException;
    List<TeaLeavesStock> getAllStockDetails(String dateBookId) throws SQLException;
}
