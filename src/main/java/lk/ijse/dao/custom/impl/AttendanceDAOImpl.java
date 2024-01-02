package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.entity.Attendance;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public ArrayList<Attendance> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Attendance entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO attendance VALUES(?,?,?,?,?,?)",entity.getAttendanceId(),entity.getDate(),entity.getEmpId(),entity.getInTime(),entity.getOutTime(),entity.isPayed());
    }

    @Override
    public boolean update(Attendance entity) throws SQLException {
        return SQLUtil.crudUtil("UPDATE attendance SET outTime=? WHERE empId=? AND date=?",entity.getOutTime(),entity.getEmpId(),entity.getDate());
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return SQLUtil.crudUtil("DELETE FROM attendance WHERE attendanceId=?",id);
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT attendanceId FROM attendance ORDER BY attendanceId DESC LIMIT 1");
        String currentAttendanceId = null;

        if (resultSet.next()){
            currentAttendanceId = resultSet.getString(1);
            return splitAttendanceId(currentAttendanceId);
        }

        return splitAttendanceId(currentAttendanceId);
    }

    @Override
    public Attendance search(String id) throws SQLException {
        return null;
    }


    @Override
    public List<Attendance> getAllAttendanceDetails(LocalDate date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM attendance WHERE date=?",date);
        List<Attendance> attendanceList = new ArrayList<>();

        while (resultSet.next()){
            Attendance attendance = new Attendance(
                    resultSet.getString("attendanceId"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("empId"),
                    resultSet.getTime("inTime").toLocalTime(),
                    resultSet.getTime("outTime").toLocalTime(),
                    resultSet.getBoolean("isPayed")
            );
            attendanceList.add(attendance);
        }
        return attendanceList;
    }

    @Override
    public boolean searchAttendance(String empId, LocalDate date) throws SQLException {
        return SQLUtil.crudUtil("SELECT * FROM attendance WHERE empId=? AND date=?",empId,date);
    }

    @Override
    public int getWorkedHoursCount(String empId) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT SUM(TIME_TO_SEC(TIMEDIFF(outTime,inTime)))/3600 FROM attendance WHERE empId=? AND isPayed=false",empId);
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getWorkedDaysCount(String empId) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT COUNT(*) FROM attendance WHERE empId=? AND isPayed=false",empId);
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean updatePayedStatus(String empId) throws SQLException {
        return SQLUtil.crudUtil("UPDATE attendance SET isPayed=true WHERE empId=? AND isPayed=false",empId);
    }

    @Override
    public int getAttendanceCount(String date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT COUNT(*) FROM attendance WHERE date=?",date);
        int count = 0;
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }

    @Override
    public boolean searchOutTime(String empId, LocalDate date) throws SQLException {
        return SQLUtil.crudUtil("SELECT * FROM attendance WHERE empId=? AND date=? AND outTime IS NOT NULL",empId,date);
    }

    private String splitAttendanceId(String currentAttendanceId) {
        if (currentAttendanceId!=null){
            String [] split = currentAttendanceId.split("A");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9) {
                selectedId++;
                return "A00" + selectedId;
            }else if (selectedId<99) {
                selectedId++;
                return "A0" + selectedId;
            }else {
                selectedId++;
                return "A" + selectedId;
            }
        }
        return "A001";
    }
}

