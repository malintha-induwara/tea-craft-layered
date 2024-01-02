package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookDto;
import lk.ijse.entity.TeaBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaBookBO extends SuperBO {
    boolean updateTeaBookAmount(String teaBookId,double amount) throws SQLException;
    String getTeaBookId(String date) throws SQLException;
    String getTeaBookDate(String teaBookId) throws SQLException ;
    boolean searchDate(String date) throws SQLException ;
    boolean createTeaBookRecord(String date) throws SQLException;
    String generateNextTeaBookId() throws SQLException;
    double getAmount(String string) throws SQLException;
    List<TeaBookDto> getAllTeaBookDetails() throws SQLException;
}
