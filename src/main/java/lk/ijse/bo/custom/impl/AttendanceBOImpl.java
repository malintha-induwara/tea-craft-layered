package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.entity.Attendance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ATTENDANCE);


    @Override
    public String generateNextAttendanceId() throws SQLException{
        return attendanceDAO.generateID();
    }

    @Override
    public boolean markAttendance(Attendance dto) throws SQLException {
        return false;
    }

    @Override
    public boolean searchAttendance(String empId, LocalDate date) throws SQLException {
        return false;
    }

    @Override
    public List<Attendance> getAllAttendanceDetails(LocalDate date) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteAttendance(String attendanceId) throws SQLException {
        return false;
    }

    @Override
    public void updateOutTime(String empId, LocalTime outTime, LocalDate currentDate) throws SQLException {

    }

    @Override
    public int getWorkedHoursCount(String empId) throws SQLException {
        return 0;
    }

    @Override
    public int getWorkedDaysCount(String empId) throws SQLException {
        return 0;
    }

    @Override
    public boolean updatePayedStatus(String empId) throws SQLException {
        return false;
    }

    @Override
    public int getAttendanceCount(String date) throws SQLException {
        return 0;
    }

    @Override
    public boolean searchOutTime(String empId, LocalDate date) throws SQLException {
        return false;
    }
}

