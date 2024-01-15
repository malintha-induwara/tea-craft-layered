package lk.ijse.teacraft.dao.custom.impl;

import lk.ijse.teacraft.dao.custom.FertilizerOrderDetailDAO;
import lk.ijse.teacraft.util.SQLUtil;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;
import java.sql.SQLException;
import java.util.List;

public class FertilizerOrderDetailDAOImpl implements FertilizerOrderDetailDAO {
    @Override
    public boolean saveFertilizerOrderDetail(String fertilizerOrderId, List<FertilizerSalesCartTm> tmList) throws SQLException {
        for (FertilizerSalesCartTm cartTm: tmList){
            if (!saveFertilizerOrderDetails(fertilizerOrderId,cartTm)){
                return false;
            }
        }
        return true;
    }

    private boolean saveFertilizerOrderDetails(String fertilizerOrderId, FertilizerSalesCartTm cartTm) throws SQLException {

      return SQLUtil.crudUtil("INSERT INTO fertilizer_order_details VALUES (?,?,?)",
                fertilizerOrderId,
                cartTm.getFertilizerId(),
                cartTm.getQty()
      );
    }


}

