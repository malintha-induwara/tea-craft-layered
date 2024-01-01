package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.view.tdm.FertilizerSalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FertilizerOrderDetailModel {


    public boolean saveFertilizerOrderDetail(String fertilizerOrderId, List<FertilizerSalesCartTm> tmList) throws SQLException {

        for (FertilizerSalesCartTm cartTm: tmList){
            if (!saveFertilizerOrderDetails(fertilizerOrderId,cartTm)){
                return false;
            }
        }
        return true;
    }

    private boolean saveFertilizerOrderDetails(String fertilizerOrderId, FertilizerSalesCartTm cartTm) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO fertilizer_order_details VALUES (?,?,?)";


        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,fertilizerOrderId);
        pstm.setString(2,cartTm.getFertilizerId());
        pstm.setInt(3,cartTm.getQty());

        return pstm.executeUpdate() > 0;

    }


}
