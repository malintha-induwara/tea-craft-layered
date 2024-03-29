package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.TeaBook;
import lk.ijse.teacraft.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TeaBookDAO extends CrudDAO<TeaBook> {
    public boolean updateTeaBookAmount(String teaBookId,double amount) throws SQLException;
    public String getTeaBookId(String date) throws SQLException;
    public String getTeaBookDate(String teaBookId) throws SQLException;
    public boolean searchDate(String date) throws SQLException;
    public boolean createTeaBookRecord(String date) throws SQLException;
    public double getAmount(String date) throws SQLException;
}
