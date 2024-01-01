package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PackagingTransactionModel {


    private final PackagingDetailsModel packagingDetailsModel= new PackagingDetailsModel();

    private final PackagingModel packagingModel = new PackagingModel();

    private final TeaTypeModel teaTypeModel = new TeaTypeModel();


    public boolean confirmPackaging(LocalDate date, List<PackagingCountAmountDto> dtoList) throws SQLException {

        boolean result = false;

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isConfirmed = packagingDetailsModel.confirmPackaging(date);

            if (isConfirmed){
                boolean isSaved = packagingModel.updatePackagingCount(dtoList);
                if (isSaved){
                    boolean isUpdated = teaTypeModel.updateAmount(dtoList);

                    if (isUpdated){
                        connection.commit();
                        result = true;
                    }
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
