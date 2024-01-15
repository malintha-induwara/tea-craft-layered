package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.TeaCraftDetailBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.TeaCraftDetailDAO;

import java.sql.SQLException;

public class TeaCraftDetailBOImpl implements TeaCraftDetailBO {

    TeaCraftDetailDAO teaCraftDetailDAO = (TeaCraftDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_CRAFT_DETAIL);


    @Override
    public double getHourlyRate() throws SQLException {
        return teaCraftDetailDAO.getHourlyRate();
    }

    @Override
    public double getOtRate() throws SQLException {
        return teaCraftDetailDAO.getOtRate();
    }

    @Override
    public double getTeaLeavesPrice() throws SQLException {
        return teaCraftDetailDAO.getTeaLeavesPrice();
    }

    @Override
    public boolean updateTeaLeavesPrice(double price) throws SQLException {
        return teaCraftDetailDAO.updateTeaLeavesPrice(price);
    }

    @Override
    public boolean updateHourlyRateAndOt(double hourlyRate, double oT) throws SQLException {
        return teaCraftDetailDAO.updateHourlyRateAndOt(hourlyRate,oT);
    }
}

