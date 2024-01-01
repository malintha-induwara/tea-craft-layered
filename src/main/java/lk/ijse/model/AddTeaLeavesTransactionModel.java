package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.TeaLeavesStock;

import java.sql.Connection;
import java.sql.SQLException;

public class AddTeaLeavesTransactionModel {



    private final TeaLeavesStockModel teaLeavesStockModel = new TeaLeavesStockModel();

    private final TeaBookModel teaBookModel = new TeaBookModel();


    public boolean saveTeaLeavesStock(TeaLeavesStock teaLeavesStockDto, String teaBookId) throws SQLException {


        boolean result = false;
        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            boolean isSaved = teaLeavesStockModel.saveTeaLeavesStock(teaLeavesStockDto);

            if (isSaved){

                double dailyAmount = teaLeavesStockModel.getTotalAmount(teaBookId);

                boolean isUpdated = teaBookModel.updateTeaBookAmount(teaBookId,dailyAmount);

                if (isUpdated){
                    connection.commit();
                    result = true;
                }
            }


        }catch (SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }

        return result;

    }
}
