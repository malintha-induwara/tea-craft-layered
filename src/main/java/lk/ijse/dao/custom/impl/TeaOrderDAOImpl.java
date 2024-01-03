package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TeaOrderDAO;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TeaOrderDAOImpl implements TeaOrderDAO {
    @Override
    public String generateNextOrderId() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT tea_order_id FROM tea_orders ORDER BY tea_order_id DESC LIMIT 1");
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


    @Override
    public boolean saveOrder(String orderId, String cusId, LocalDate date) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO tea_orders VALUES(?,?,?)",orderId,cusId,java.sql.Date.valueOf(date));
    }

    @Override
    public int getOrderCount(String date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT COUNT(tea_order_id) FROM tea_orders WHERE date=?",date);
        int count = 0;
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}

