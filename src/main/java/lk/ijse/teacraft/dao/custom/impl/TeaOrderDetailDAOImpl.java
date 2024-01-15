package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TeaOrderDetailDAO;
import lk.ijse.util.SQLUtil;
import lk.ijse.teacraft.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.List;

public class TeaOrderDetailDAOImpl implements TeaOrderDetailDAO {
    @Override
    public boolean saveOrderDetail(String orderId, List<SalesCartTm> tmList) throws SQLException {
        for (SalesCartTm salesCartTm : tmList) {
            if (!saveOrderDetail(orderId,salesCartTm)){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean saveOrderDetail(String orderId, SalesCartTm salesCartTm) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO tea_order_details VALUES(?,?,?)",orderId,salesCartTm.getPackId(),salesCartTm.getQty());
    }
}

