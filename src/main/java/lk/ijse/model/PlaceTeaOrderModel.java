package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PlaceTeaOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceTeaOrderModel {




    private final PackagingModel packagingModel = new PackagingModel();

    private final TeaOrderDetailModel teaOrderDetailModel = new TeaOrderDetailModel();



    public boolean placeOrder(PlaceTeaOrderDto dto) throws SQLException {



        boolean result = false;
        Connection connection = null;

        try
        {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            boolean isOrderSaved = TeaOrderModel.saveOrder(dto.getOrderId(), dto.getCusId(), dto.getDate());

            if (isOrderSaved){
                boolean isUpdated = packagingModel.updatePackaging(dto.getTmList());

                if (isUpdated){

                    boolean isOrderDetailSaved = teaOrderDetailModel.saveOrderDetail(dto.getOrderId(),dto.getTmList());

                    if (isOrderDetailSaved){
                        connection.commit();
                        result = true;
                    }
                }

            }

        }
        catch (SQLException e) {
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
