package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.entity.TeaBookType;
import lk.ijse.teacraft.entity.TeaBookTypeDetails;
import lk.ijse.teacraft.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface TeaBookTypeDAO extends CrudDAO<TeaBookType> {
    public List<TeaBookTypeDetails> getTotalAmount(LocalDate date) throws SQLException;
    public List<TeaBookType> getAllTeaBookTypeDetails(String date) throws SQLException;
    public boolean confirmTeaBook(LocalDate date) throws SQLException;
}
