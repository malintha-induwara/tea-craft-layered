package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.SuperDAO;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface FertilizerOrderDetailDAO extends SuperDAO {

 boolean saveFertilizerOrderDetail(String fertilizerOrderId, List<FertilizerSalesCartTm> tmList) throws SQLException;


}
