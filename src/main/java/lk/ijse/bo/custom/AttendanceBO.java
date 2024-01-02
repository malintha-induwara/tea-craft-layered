package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Attendance;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    String generateNextAttendanceId() throws SQLException, ClassNotFoundException;
    boolean markAttendance(Attendance dto) throws SQLException;
    boolean searchAttendance(String empId, LocalDate date) throws SQLException;
    List<Attendance> getAllAttendanceDetails(LocalDate date) throws SQLException;
    boolean deleteAttendance(String attendanceId) throws SQLException ;
    void updateOutTime(String empId, LocalTime outTime, LocalDate currentDate) throws SQLException ;
    int getWorkedHoursCount(String empId) throws SQLException;
    int getWorkedDaysCount(String empId) throws SQLException ;
    boolean updatePayedStatus(String empId) throws SQLException;
    int getAttendanceCount(String date) throws SQLException;
    boolean searchOutTime(String empId, LocalDate date) throws SQLException;
}
