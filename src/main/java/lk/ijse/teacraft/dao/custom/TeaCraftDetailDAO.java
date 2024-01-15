package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.SuperDAO;
import lk.ijse.teacraft.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TeaCraftDetailDAO extends SuperDAO {
    double getHourlyRate() throws SQLException;
    double getOtRate() throws SQLException;
    double getTeaLeavesPrice() throws SQLException;
    boolean updateTeaLeavesPrice(double price) throws SQLException;
    boolean updateHourlyRateAndOt(double hourlyRate, double oT) throws SQLException;
}
