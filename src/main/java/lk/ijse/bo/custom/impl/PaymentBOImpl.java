package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentsDAO;
import lk.ijse.dao.custom.TeaLeavesStockDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PaymentsDto;
import lk.ijse.entity.Payments;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentsDAO paymentsDAO = (PaymentsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENTS);

    TeaLeavesStockDAO teaLeavesStockDAO = (TeaLeavesStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_LEAVES_STOCK);


    @Override
    public String generateNextPaymentId() throws SQLException {
        return paymentsDAO.generateID();
    }

    @Override
    public boolean savePayments(PaymentsDto dto) throws SQLException {
        return paymentsDAO.save(new Payments(
                dto.getPaymentId(),
                dto.getSupId(),
                dto.getAmount(),
                dto.getPayment(),
                dto.getDate()
        ));
    }


    @Override
    public List<PaymentsDto> getAllPaymentsDetails(String supId) throws SQLException {
        List<Payments> all = paymentsDAO.getAllPaymentsDetails(supId);
        List<PaymentsDto> dtoList = new ArrayList<>();
        for (Payments payments: all){
            dtoList.add(new PaymentsDto(
                    payments.getPaymentId(),
                    payments.getSupId(),
                    payments.getAmount(),
                    payments.getPayment(),
                    payments.getDate()
            ));
        }
        return dtoList;
    }

    @Override
    public boolean addPayment(PaymentsDto dto) throws SQLException {
        boolean result = false;

        Connection connection = null;
        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isPaymentSaved = savePayments(dto);

            if (isPaymentSaved){
                boolean isUpdated = teaLeavesStockDAO.updatePayedStatus(dto.getSupId());

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

