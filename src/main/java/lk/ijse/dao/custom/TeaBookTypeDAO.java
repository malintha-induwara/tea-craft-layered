package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.TeaBookType;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface TeaBookTypeDAO extends CrudDAO<TeaBookType> {
    public List<TeaBookTypeDetailDto> getTotalAmount(LocalDate date) throws SQLException, ClassNotFoundException;
    public List<TeaBookType> getAllTeaBookTypeDetails(String date) throws SQLException, ClassNotFoundException;
    public boolean confirmTeaBook(LocalDate date) throws SQLException, ClassNotFoundException;
}
