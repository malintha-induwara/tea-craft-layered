package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TeaOrderModel {


    public String generateNextOrderId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT tea_order_id FROM tea_orders ORDER BY tea_order_id DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentOrderId = null;

        if (resultSet.next()){
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }


        return splitOrderId(currentOrderId);
    }

    private String splitOrderId(String currentOrderId) {

        if (currentOrderId!= null){
            String [] split = currentOrderId.split("O");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9){
                selectedId++;
                return "O00"+selectedId;
            }else if (selectedId<99){
                selectedId++;
                return "O0"+selectedId;
            }else {
                selectedId++;
                return "O"+selectedId;
            }
        }
        return "O001";
    }

    public static boolean saveOrder(String orderId, String cusId, LocalDate date) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_orders VALUES(?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,orderId);
        pstm.setString(2,cusId);
        pstm.setDate(3,java.sql.Date.valueOf(date));

        return pstm.executeUpdate()>0;

    }

    public int getOrderCount(String date) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(tea_order_id) FROM tea_orders WHERE date=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,date);

        ResultSet resultSet = pstm.executeQuery();

        int count = 0;

        if (resultSet.next()){
           count = resultSet.getInt(1);
        }

        return count;
    }
}
