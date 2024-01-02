package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TeaBookDAO;
import lk.ijse.entity.TeaBook;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeaBookDAOImpl implements TeaBookDAO {
    @Override
    public ArrayList<TeaBook> getAll() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_book order by date");
        ArrayList<TeaBook> teaBooks = new ArrayList<>();
        while (resultSet.next()){
           teaBooks.add(new TeaBook(
                   resultSet.getString(1),
                   resultSet.getDouble(2),
                   resultSet.getString(3)
           ));
        }
       return teaBooks;
    }

    @Override
    public boolean save(TeaBook dto) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO tea_book VALUES(?,?,?)",
                dto.getTeaBookId(),
                dto.getDailyAmount(),
                dto.getDate());
    }

    @Override
    public boolean update(TeaBook dto) throws SQLException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return false;
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT teaBookId FROM tea_book ORDER BY teaBookId DESC LIMIT 1");
        String currentTeaBookId = null;

        if (resultSet.next()){
            currentTeaBookId = resultSet.getString(1);
            return splitTeaBookId(currentTeaBookId);
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

    @Override
    public TeaBook search(String id) throws SQLException {
        return null;
    }

    public boolean updateTeaBookAmount(String teaBookId,double amount) throws SQLException {
        return SQLUtil.crudUtil("UPDATE tea_book SET dailyAmount=? WHERE teaBookId=?",
                amount,
                teaBookId);
    }

    public String getTeaBookId(String date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT teaBookId FROM tea_book WHERE date=?",date);
        resultSet.next();
        return resultSet.getString(1);
    }

    public String getTeaBookDate(String teaBookId) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT date FROM tea_book WHERE teaBookId=?",teaBookId);
        resultSet.next();
        return resultSet.getString(1);
    }

    public boolean searchDate(String date) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_book WHERE date=?",date);
        return resultSet.next();
    }
    public boolean createTeaBookRecord(String date) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO tea_book VALUES(?,?,?)",
                generateID(),
                0.0,
                date);
    }
    public double getAmount(String date) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT dailyAmount FROM tea_book WHERE date=?",date);
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }

}

