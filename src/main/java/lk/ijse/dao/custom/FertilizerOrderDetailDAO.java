package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.view.tdm.FertilizerSalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface FertilizerOrderDetailDAO extends SuperDAO {

 boolean saveFertilizerOrderDetail(String fertilizerOrderId, List<FertilizerSalesCartTm> tmList) throws SQLException;


}
