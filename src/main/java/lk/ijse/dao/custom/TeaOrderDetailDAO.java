package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.view.tdm.SalesCartTm;
import java.sql.SQLException;
import java.util.List;

public interface TeaOrderDetailDAO extends SuperDAO {
    boolean saveOrderDetail(String orderId, List<SalesCartTm> tmList) throws SQLException;
    boolean saveOrderDetail(String orderId, SalesCartTm salesCartTm) throws SQLException;
}
