package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Payments;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentsModel {


    public String generateNextPaymentId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT paymentId FROM payments ORDER BY paymentId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentPaymentId = null;

        if (resultSet.next()) {
            currentPaymentId = resultSet.getString(1);
            return splitPaymentId(currentPaymentId);
        }


        return splitPaymentId(currentPaymentId);
    }

    private String splitPaymentId(String currentPaymentId) {

        if (currentPaymentId != null) {
            String[] split = currentPaymentId.split("P");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9) {
                selectedId++;
                return "P00" + selectedId;
            } else if (selectedId < 99) {
                selectedId++;
                return "P0" + selectedId;
            } else {
                selectedId++;
                return "P" + selectedId;
            }
        }
        return "P001";
    }



    public boolean savePayments(Payments dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payments VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getPaymentId());
        pstm.setString(2,dto.getSupId());
        pstm.setDouble(3,dto.getAmount());
        pstm.setDouble(4,dto.getPayment());
        pstm.setDate(5, Date.valueOf(dto.getDate()));

        return pstm.executeUpdate()>0;


    }

    public List<Payments> getAllPaymentsDetails(String supId) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM payments WHERE supId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,supId);

            List<Payments> dtoList = new ArrayList<>();

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                String paymentId = resultSet.getString(1);
                String supId1 = resultSet.getString(2);
                double amount = resultSet.getDouble(3);
                double payment = resultSet.getDouble(4);
                LocalDate date = resultSet.getDate(5).toLocalDate();

                Payments dto = new Payments(paymentId,supId1,amount,payment,date);

                dtoList.add(dto);
            }


        return dtoList;
    }
}
