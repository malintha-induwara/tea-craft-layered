package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import java.sql.SQLException;

public interface TeaCraftDetailBO extends SuperBO {
    double getHourlyRate() throws SQLException;
    double getOtRate() throws SQLException;
    double getTeaLeavesPrice() throws SQLException;
    boolean updateTeaLeavesPrice(double price) throws SQLException;
    boolean updateHourlyRateAndOt(double hourlyRate, double oT) throws SQLException;
}
