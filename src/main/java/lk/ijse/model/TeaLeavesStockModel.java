package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.TeaLeavesStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaLeavesStockModel {


    public String generateNextTeaLeavesStockId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();


        String sql = "SELECT teaStockId FROM tea_leaves_stock ORDER BY teaStockId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentTeaLeavesStockId = null;

        if (resultSet.next()){
            currentTeaLeavesStockId = resultSet.getString(1);
            return splitTeaLeavesStockId(currentTeaLeavesStockId);
        }

        return splitTeaLeavesStockId(currentTeaLeavesStockId);
    }

    private String splitTeaLeavesStockId(String currentTeaLeavesStockId) {

      if (currentTeaLeavesStockId!=null){
          String[] split = currentTeaLeavesStockId.split("TS");
          int selectedId = Integer.parseInt(split[1]);
          if (selectedId < 9){
              selectedId++;
              return "TS00"+selectedId;
          }else if (selectedId < 99){
              selectedId++;
              return "TS0"+selectedId;
          }else {
              selectedId++;
              return "TS"+selectedId;
          }
      }

      return "TS001";
    }



    public boolean saveTeaLeavesStock(TeaLeavesStock dto) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

       String sql = "INSERT INTO tea_leaves_stock VALUES(?,?,?,?,?)";

       PreparedStatement pstm = connection.prepareStatement(sql);

         pstm.setString(1,dto.getTeaStockId());
         pstm.setString(2,dto.getSupId());
         pstm.setString(3,dto.getTeaBookId());
         pstm.setDouble(4,dto.getAmount());
         pstm.setBoolean(5,dto.isPayed());


         return pstm.executeUpdate()>0;

    }

    public double getTotalAmount(String teaBookId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(amount) FROM tea_leaves_stock WHERE teaBookId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,teaBookId);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getDouble(1);
    }

    public List<TeaLeavesStock> getAllStockDetails(String dateBookId) throws SQLException {

           Connection connection = DbConnection.getInstance().getConnection();

           String sql = "SELECT * FROM tea_leaves_stock WHERE teaBookId=?";

           PreparedStatement pstm = connection.prepareStatement(sql);

           pstm.setString(1,dateBookId);

           List<TeaLeavesStock> dtoList = new ArrayList<>();

           ResultSet resultSet = pstm.executeQuery();


                while (resultSet.next()){

                    String teaStockId = resultSet.getString(1);
                    String supId = resultSet.getString(2);
                    String teaBookId = resultSet.getString(3);
                    double amount = resultSet.getDouble(4);
                    boolean isPayed = resultSet.getBoolean(5);

                    TeaLeavesStock dto = new TeaLeavesStock(teaStockId,supId,teaBookId,amount,isPayed);
                    dtoList.add(dto);
                }

            return dtoList;
    }

    public TeaLeavesStock searchTeaLeavesStock(String text) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_leaves_stock WHERE teaStockId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        TeaLeavesStock dto = null;

        if (resultSet.next()){
            String teaStockId = resultSet.getString(1);
            String supId = resultSet.getString(2);
            String teaBookId = resultSet.getString(3);
            double amount = resultSet.getDouble(4);
            boolean isPayed = resultSet.getBoolean(5);

            dto = new TeaLeavesStock(teaStockId,supId,teaBookId,amount,isPayed);
        }

        return dto;
    }

    public boolean deleteTeaLeavesStock(String teaStockId) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM tea_leaves_stock WHERE teaStockId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,teaStockId);

            return pstm.executeUpdate()>0;

    }

    public boolean updateTeaLeavesStock(TeaLeavesStock teaLeavesStock) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_leaves_stock SET supId=?,teaBookId=?,amount=? WHERE teaStockId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, teaLeavesStock.getSupId());
        pstm.setString(2, teaLeavesStock.getTeaBookId());
        pstm.setDouble(3, teaLeavesStock.getAmount());
        pstm.setString(4, teaLeavesStock.getTeaStockId());

        return pstm.executeUpdate()>0;
    }

    public double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(amount) FROM tea_leaves_stock WHERE supId=? and isPayed=false";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,supId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return resultSet.getDouble(1);
        }

        return 0;
    }

    public boolean updatePayedStatus(String supId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_leaves_stock SET isPayed=true WHERE supId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,supId);

        return pstm.executeUpdate()>0;
    }
}

