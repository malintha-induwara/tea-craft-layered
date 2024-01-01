package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Attendance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceDAO extends CrudDAO<Attendance> {
    List<Attendance> getAllAttendanceDetails(LocalDate date) throws SQLException, ClassNotFoundException;
    boolean searchAttendance(String empId, LocalDate date) throws SQLException, ClassNotFoundException;
    int getWorkedHoursCount(String empId) throws SQLException, ClassNotFoundException;
    int getWorkedDaysCount(String empId) throws SQLException, ClassNotFoundException;
    boolean updatePayedStatus(String empId) throws SQLException, ClassNotFoundException;
    int getAttendanceCount(String date) throws SQLException, ClassNotFoundException;
    boolean searchOutTime(String empId, LocalDate date) throws SQLException, ClassNotFoundException;
}
