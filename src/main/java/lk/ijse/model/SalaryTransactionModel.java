package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Salary;

import java.sql.Connection;
import java.sql.SQLException;

public class SalaryTransactionModel {


    private final SalaryModel salaryModel = new SalaryModel();

    private final AttendanceModel attendanceModel = new AttendanceModel();

    public boolean addSalary(Salary dto) throws SQLException {

        boolean result = false;

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSalarySaved = salaryModel.addSalary(dto);

            if (isSalarySaved) {
                boolean isUpdated = attendanceModel.updatePayedStatus(dto.getEmpId());

                if (isUpdated) {
                    connection.commit();
                    result = true;
                }
            }
        } catch (SQLException e){
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return result;
    }
}
