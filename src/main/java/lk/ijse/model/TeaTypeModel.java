package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.TeaTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeModel {

    private  final  PackagingModel packagingModel = new PackagingModel();

    public List<TeaTypes> getAllTeaTypes() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_types";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<TeaTypes> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

       while(resultSet.next()) {

            String typeId = resultSet.getString(1);
            String type = resultSet.getString(2);
            double amount = resultSet.getDouble(3);

            TeaTypes dto = new TeaTypes(typeId,type,amount);
            dtoList.add(dto);

       }

       return  dtoList;

    }

    public String getTeaTypeId(String type) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT typeId FROM tea_types WHERE type=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,type);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getString(1);


    }

    public String getTeaType(String typeId) throws SQLException {


        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT type FROM tea_types WHERE typeId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,typeId);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getString(1);

    }


    public boolean updateTeaTypeAmount(List<TeaBookTypeDetailDto> dtoList) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

       String sql = "UPDATE tea_types SET amount = amount + ? WHERE typeId = ?";

         PreparedStatement pstm = connection.prepareStatement(sql);

            for (TeaBookTypeDetailDto dto : dtoList) {
                pstm.setDouble(1,dto.getAmount());
                pstm.setString(2,dto.getTypeId());
                if (pstm.executeUpdate()<=0){
                    return false;
                }
            }
        return true;
    }

    public double getTeaAmount(String teaType) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

         String sql = "SELECT amount FROM tea_types WHERE type=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,teaType);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                return resultSet.getDouble(1);
            }
        return 0;
    }

    public boolean updateAmount(List<PackagingCountAmountDto> dtoList) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_types SET amount = amount - ? WHERE typeId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        for (PackagingCountAmountDto dto : dtoList) {

            String typeId = packagingModel.getTypeId(dto.getPackId());

            pstm.setDouble(1,dto.getAmount());
            pstm.setString(2,typeId);

            if (pstm.executeUpdate()<=0){
                return false;
            }

        }

        return true;
    }
}
