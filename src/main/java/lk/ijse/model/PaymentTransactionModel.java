package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Payments;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentTransactionModel {


    private final PaymentsModel paymentsModel = new PaymentsModel();

    private final TeaLeavesStockModel teaLeavesStockModel = new TeaLeavesStockModel();


    public boolean savePayment(Payments dto) throws SQLException {

        boolean result = false;

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isPaymentSaved = paymentsModel.savePayments(dto);

            if (isPaymentSaved){
                boolean isUpdated = teaLeavesStockModel.updatePayedStatus(dto.getSupId());

                if (isUpdated){
                    connection.commit();
                    result = true;
                }
            }
        }catch (SQLException e){
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;

    }
}
