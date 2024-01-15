package lk.ijse.teacraft.dao.custom.impl;

import lk.ijse.teacraft.dao.custom.TeaCraftDetailDAO;
import lk.ijse.teacraft.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeaCraftDetailDAOImpl implements TeaCraftDetailDAO {
    @Override
    public double getHourlyRate() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT hourlyRate FROM teacraft_details where detailsId=?","TD001");
        double hourlyRate = 0;
        if (resultSet.next()){
            hourlyRate=resultSet.getDouble(1);
        }
        return hourlyRate;
    }

    @Override
    public double getOtRate() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT oT FROM teacraft_details where detailsId=?","TD001");
        double otRate = 0;
        if (resultSet.next()){
            otRate=resultSet.getDouble(1);
        }
        return otRate;
    }

    @Override
    public double getTeaLeavesPrice() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT priceOfTeaLeaves FROM teacraft_details where detailsId=?","TD001");
        double priceOfTeaLeaves = 0;
        if (resultSet.next()){
            priceOfTeaLeaves=resultSet.getDouble(1);
        }
        return priceOfTeaLeaves;
    }

    @Override
    public boolean updateTeaLeavesPrice(double price) throws SQLException {
      return SQLUtil.crudUtil("UPDATE teacraft_details SET priceOfTeaLeaves=? WHERE detailsId=?",price,"TD001");
    }

    @Override
    public boolean updateHourlyRateAndOt(double hourlyRate, double oT) throws SQLException {
        return SQLUtil.crudUtil("UPDATE teacraft_details SET hourlyRate=?,oT=? WHERE detailsId=?",hourlyRate,oT,"TD001");
    }
}

