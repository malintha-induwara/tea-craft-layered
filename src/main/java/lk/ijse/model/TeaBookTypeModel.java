package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.TeaBookType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeaBookTypeModel {


    public String generateNextTeaBookTypeId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT teaBookTypeId FROM tea_book_type_details ORDER BY teaBookTypeId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentTeaBookTypeId = null;

        if(resultSet.next()){
            currentTeaBookTypeId = resultSet.getString(1);

            return splitTeaBookTypeId(currentTeaBookTypeId);
        }

        return splitTeaBookTypeId(currentTeaBookTypeId);
    }

    private String splitTeaBookTypeId(String currentTeaBookTypeId) {

        if (currentTeaBookTypeId != null){
            String[] split = currentTeaBookTypeId.split("TBT-");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9){
                selectedId++;
                return "TBT-00"+selectedId;
            }else if (selectedId < 99){
                selectedId++;
                return "TBT-0"+selectedId;
            }else {
                selectedId++;
                return "TBT-"+selectedId;
            }

         }

        return "TBT-001";
    }

    public boolean saveTeaBookType(TeaBookType dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_book_type_details VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getTeaBookTypeId());
        pstm.setDate(2, Date.valueOf(dto.getDate()));
        pstm.setString(3,dto.getTypeId());
        pstm.setDouble(4,dto.getAmount());
        pstm.setBoolean(5,dto.isConfirmed());

        return pstm.executeUpdate()>0;

    }

    public List<TeaBookType> getAllTeaBookTypeDetails(String date) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

         String sql = "SELECT * FROM tea_book_type_details WHERE date = ?";

         PreparedStatement pstm = connection.prepareStatement(sql);

         pstm.setString(1,date);

         ResultSet resultSet = pstm.executeQuery();

         List<TeaBookType> dtoList = new ArrayList<>();

         while (resultSet.next()){
                TeaBookType dto = new TeaBookType(
                        resultSet.getString(1),
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getBoolean(5)
                );

             dtoList.add(dto);
         }


        return dtoList;
    }

    public boolean deleteTeaBookType(String teaBookTypeId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tea_book_type_details WHERE teaBookTypeId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,teaBookTypeId);

        return pstm.executeUpdate()>0;

    }

    //Transaction
    public boolean confirmTeaBook(LocalDate date) throws SQLException {


        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_book_type_details SET isConfirmed = true WHERE date = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDate(1, Date.valueOf(date));

        return pstm.executeUpdate()>0;

    }

    public List<TeaBookTypeDetailDto> getTotalAmount(LocalDate date) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT typeId,SUM(amount) FROM tea_book_type_details WHERE date = ? AND isConfirmed = false group by typeId";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDate(1,Date.valueOf(date));


        List<TeaBookTypeDetailDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();



        while (resultSet.next()) {
            String typeId= resultSet.getString(1);
            double amount = resultSet.getDouble(2);

            TeaBookTypeDetailDto dto = new TeaBookTypeDetailDto(typeId,amount);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
