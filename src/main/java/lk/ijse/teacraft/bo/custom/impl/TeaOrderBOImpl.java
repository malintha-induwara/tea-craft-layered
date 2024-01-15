package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.TeaOrderBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.PackagingDAO;
import lk.ijse.teacraft.dao.custom.TeaOrderDAO;
import lk.ijse.teacraft.dao.custom.TeaOrderDetailDAO;
import lk.ijse.teacraft.dto.PlaceTeaOrderDto;
import lk.ijse.teacraft.entity.TeaOrder;
import lk.ijse.teacraft.util.TransactionUtil;
import lk.ijse.teacraft.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.List;

public class TeaOrderBOImpl implements TeaOrderBO {

    PackagingDAO packagingDAO = (PackagingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING);

    TeaOrderDAO teaOrderDAO = (TeaOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_ORDER);

    TeaOrderDetailDAO teaOrderDetailDAO = (TeaOrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_ORDER_DETAIL);

    @Override
    public String generateNextOrderId() throws SQLException {
        return teaOrderDAO.generateNextOrderId();
    }


    @Override
    public int getOrderCount(String date) throws SQLException {
        return teaOrderDAO.getOrderCount(date);
    }


    public boolean saveOrderDetail(String orderId, List<SalesCartTm> tmList) throws SQLException {
        for (SalesCartTm salesCartTm : tmList) {
            if (!saveOrderDetail(orderId,salesCartTm)){
                return false;
            }
        }
        return true;
    }

    public boolean saveOrderDetail(String orderId, SalesCartTm salesCartTm) throws SQLException {
       return teaOrderDetailDAO.saveOrderDetail(orderId,salesCartTm);
    }


    public boolean placeOrder(PlaceTeaOrderDto dto) throws SQLException {


        boolean result = false;


        try
        {
            TransactionUtil.autoCommitFalse();

            boolean isOrderSaved = teaOrderDAO.saveOrder(new TeaOrder(dto.getOrderId(),dto.getCusId(),dto.getDate()));

            if (isOrderSaved){
                boolean isUpdated = packagingDAO.updatePackaging(dto.getTmList());

                if (isUpdated){

                    boolean isOrderDetailSaved = saveOrderDetail(dto.getOrderId(),dto.getTmList());

                    if (isOrderDetailSaved){
                        TransactionUtil.commit();
                        result = true;
                    }
                }

            }
        }
        catch (SQLException e) {
            TransactionUtil.rollback();
        }

        return result;
    }

}

