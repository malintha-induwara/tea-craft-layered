package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.view.tdm.SalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TeaOrderDetailModel {





    public boolean saveOrderDetail(String orderId, SalesCartTm salesCartTm) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_order_details VALUES(?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,orderId);
        pstm.setString(2,salesCartTm.getPackId());
        pstm.setInt(3,salesCartTm.getQty());

        return pstm.executeUpdate()>0;

    }


}
