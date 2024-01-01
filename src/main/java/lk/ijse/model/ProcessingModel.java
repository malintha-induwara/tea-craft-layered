package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookTypeDetailDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProcessingModel {


    private final TeaBookTypeModel teaBookTypeModel = new TeaBookTypeModel();

    private final TeaTypeModel teaTypeModel = new TeaTypeModel();

    public boolean updateDetails(LocalDate date, List<TeaBookTypeDetailDto> dtoList) throws SQLException {
        boolean result = false;

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            boolean isConfirmed = teaBookTypeModel.confirmTeaBook(date);

            if (isConfirmed){
                boolean isUpdated = teaTypeModel.updateTeaTypeAmount(dtoList);

                if (isUpdated){
                    connection.commit();
                    result = true;
                }
            }
        }catch (SQLException e){
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return result;
    }
}
