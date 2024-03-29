package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.Attendance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceDAO extends CrudDAO<Attendance> {
    List<Attendance> getAllAttendanceDetails(LocalDate date) throws SQLException;
    boolean searchAttendance(String empId, LocalDate date) throws SQLException;
    int getWorkedHoursCount(String empId) throws SQLException;
    int getWorkedDaysCount(String empId) throws SQLException;
    boolean updatePayedStatus(String empId) throws SQLException;
    int getAttendanceCount(String date) throws SQLException;
    boolean searchOutTime(String empId, LocalDate date) throws SQLException;
    void updateOutTime(String empId, LocalTime outTime, LocalDate currentDate) throws SQLException;
}
