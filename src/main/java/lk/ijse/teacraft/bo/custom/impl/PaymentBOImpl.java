package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.PaymentBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.PaymentsDAO;
import lk.ijse.teacraft.dao.custom.TeaLeavesStockDAO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.PaymentsDto;
import lk.ijse.teacraft.entity.Payments;
import lk.ijse.teacraft.util.TransactionUtil;

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

        try{
            TransactionUtil.autoCommitFalse();

            boolean isPaymentSaved = savePayments(dto);

            if (isPaymentSaved){
                boolean isUpdated = teaLeavesStockDAO.updatePayedStatus(dto.getSupId());

                if (isUpdated){
                    TransactionUtil.commit();
                    result = true;
                }
            }
        }catch (SQLException e){
            TransactionUtil.rollback();
        }
        return result;
    }
}

