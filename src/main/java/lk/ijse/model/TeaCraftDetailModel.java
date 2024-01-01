package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeaCraftDetailModel {

    public double getHourlyRate() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT hourlyRate FROM teacraft_details where detailsId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, "TD001");

        double hourlyRate = 0;

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            hourlyRate = resultSet.getDouble(1);
        }

        return hourlyRate;
    }

    public double getOtRate() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT oT FROM teacraft_details where detailsId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, "TD001");
        double otRate = 0;

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            otRate = resultSet.getDouble(1);
        }

        return otRate;
    }

    public double getTeaLeavesPrice() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT priceOfTeaLeaves FROM teacraft_details where detailsId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, "TD001");

        double teaLeavesPrice = 0;

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            teaLeavesPrice = resultSet.getDouble(1);
        }

        return teaLeavesPrice;
    }

    public boolean updateTeaLeavesPrice(double price) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE teacraft_details SET priceOfTeaLeaves=? WHERE detailsId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1, price);
        pstm.setString(2, "TD001");


        return pstm.executeUpdate() > 0;

    }

    public boolean updateHourlyRateAndOt(double hourlyRate, double oT) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE teacraft_details SET hourlyRate=?,oT=? WHERE detailsId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1, hourlyRate);
        pstm.setDouble(2, oT);

        pstm.setString(3, "TD001");

        return pstm.executeUpdate() > 0;
    }
}
