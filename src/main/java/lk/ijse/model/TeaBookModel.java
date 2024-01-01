package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.TeaBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaBookModel {



    public boolean updateTeaBookAmount(String teaBookId,double amount) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_book SET dailyAmount=? WHERE teaBookId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setDouble(1,amount);
        pstm.setString(2,teaBookId);

        return pstm.executeUpdate()>0;

    }


    public String getTeaBookId(String date) throws SQLException {

       Connection connection = DbConnection.getInstance().getConnection();

         String sql = "SELECT teaBookId FROM tea_book WHERE date=?";

         PreparedStatement pstm = connection.prepareStatement(sql);

         pstm.setString(1,date);

         ResultSet resultSet = pstm.executeQuery();

         resultSet.next();

         return resultSet.getString(1);
    }



    public String getTeaBookDate(String teaBookId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT date FROM tea_book WHERE teaBookId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,teaBookId);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getString(1);
    }






    public boolean searchDate(String date) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_book WHERE date=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,date);

        ResultSet resultSet = pstm.executeQuery();

        return resultSet.next();

    }

    public boolean createTeaBookRecord(String date) throws SQLException {

        String teaBookId = generateNextTeaBookId();
        double amount = 0.0;


        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_book VALUES(?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,teaBookId);
        pstm.setDouble(2,amount);
        pstm.setString(3,date);

        return pstm.executeUpdate()>0;
    }


    public String generateNextTeaBookId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT teaBookId FROM tea_book ORDER BY teaBookId DESC LIMIT 1";

        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentTeaBookId = null;

        if (resultSet.next()){
            currentTeaBookId = resultSet.getString(1);
            return  splitTeaBookId(currentTeaBookId);

        }
        return splitTeaBookId(currentTeaBookId);
    }

    private String splitTeaBookId(String currentTeaBookId) {

        if (currentTeaBookId != null){
            String[] split = currentTeaBookId.split("TB");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9){
                selectedId++;
                return "TB00"+selectedId;
            }else if (selectedId < 99){
                selectedId++;
                return "TB0"+selectedId;
            }else {
                selectedId++;
                return "TB"+selectedId;
            }

        }
        return "TB001";
    }


    public double getAmount(String string) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT dailyAmount FROM tea_book WHERE date=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,string);

        ResultSet resultSet= pstm.executeQuery();

        double amount = 0.0;

        if (resultSet.next()){
             amount = resultSet.getDouble(1);
        }

        return amount;
    }


    public List<TeaBook> getAllTeaBookDetails() throws SQLException {


        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_book order by date";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<TeaBook> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String teaBookId = resultSet.getString(1);
            double dailyAmount = resultSet.getDouble(2);
            String date = resultSet.getString(3);

            TeaBook dto = new TeaBook(teaBookId,dailyAmount,date);

            dtoList.add(dto);

        }

        return dtoList;
    }
}
