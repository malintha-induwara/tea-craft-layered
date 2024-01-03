package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.dto.TeaBookTypeDto;
import lk.ijse.entity.TeaBookType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface TeaBookTypeBO extends SuperBO {
    String generateNextTeaBookTypeId() throws SQLException;
    boolean saveTeaBookType(TeaBookTypeDto dto) throws SQLException;
    List<TeaBookTypeDto> getAllTeaBookTypeDetails(String date) throws SQLException;
    boolean deleteTeaBookType(String teaBookTypeId) throws SQLException;
    boolean confirmTeaBook(LocalDate date) throws SQLException;
    List<TeaBookTypeDetailDto> getTotalAmount(LocalDate date) throws SQLException;
}
