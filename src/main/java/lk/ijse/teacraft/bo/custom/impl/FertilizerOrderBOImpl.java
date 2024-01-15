package lk.ijse.teacraft.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.teacraft.bo.custom.FertilizerOrderBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.FertilizerDAO;
import lk.ijse.teacraft.dao.custom.FertilizerOrderDAO;
import lk.ijse.teacraft.dao.custom.FertilizerOrderDetailDAO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.PlaceFertilizerOrderDto;
import lk.ijse.teacraft.entity.FertilizerOrder;
import lk.ijse.teacraft.util.TransactionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class FertilizerOrderBOImpl implements FertilizerOrderBO {

    FertilizerDAO fertilizerDAO = (FertilizerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FERTILIZER);
    FertilizerOrderDAO fertilizerOrderDAO = (FertilizerOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FERTILIZER_ORDER);
    FertilizerOrderDetailDAO fertilizerOrderDetailDAO = (FertilizerOrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FERTILIZER_ORDER_DETAIL);


    @Override
    public String generateNextFertilizerOrderId() throws SQLException {
        return fertilizerOrderDAO.generateID();
    }

    @Override
    public boolean saveFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException {
        return fertilizerOrderDAO.save(new FertilizerOrder(
                dto.getFertilizerOrderId(),
                dto.getCustomerId(),
                dto.getDate()
        ));
    }

    @Override
    public boolean placeFertilizerOrder(PlaceFertilizerOrderDto dto) throws SQLException {
        boolean result = false;

        try{

            TransactionUtil.autoCommitFalse();

            boolean isOrderSaved = saveFertilizerOrder(dto);

            if(isOrderSaved) {
                boolean isUpdated = fertilizerDAO.updateFertilizer(dto.getTmList());

                if (isUpdated){
                    boolean isFertilizerOrderDetailSaved = fertilizerOrderDetailDAO.saveFertilizerOrderDetail(dto.getFertilizerOrderId(),dto.getTmList());
                    if (isFertilizerOrderDetailSaved){
                        TransactionUtil.commit();
                        result = true;

                    }
                }
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            TransactionUtil.rollback();
        }

        return result;
    }

}

