package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentsDto;
import java.sql.*;
import java.util.List;

public interface PaymentBO extends SuperBO {
    String generateNextPaymentId() throws SQLException;
    boolean savePayments(PaymentsDto dto) throws SQLException;
    boolean addPayment(PaymentsDto dto) throws SQLException;
    List<PaymentsDto> getAllPaymentsDetails(String supId) throws SQLException;
}
