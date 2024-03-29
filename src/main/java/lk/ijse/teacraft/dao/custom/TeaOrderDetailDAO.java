package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.SuperDAO;
import lk.ijse.teacraft.view.tdm.SalesCartTm;
import java.sql.SQLException;
import java.util.List;

public interface TeaOrderDetailDAO extends SuperDAO {
    boolean saveOrderDetail(String orderId, List<SalesCartTm> tmList) throws SQLException;
    boolean saveOrderDetail(String orderId, SalesCartTm salesCartTm) throws SQLException;
}
