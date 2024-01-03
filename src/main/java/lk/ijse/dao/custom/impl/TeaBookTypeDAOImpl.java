package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TeaBookTypeDAO;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.TeaBookType;
import lk.ijse.entity.TeaBookTypeDetails;
import lk.ijse.util.SQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeaBookTypeDAOImpl implements TeaBookTypeDAO {
    @Override
    public ArrayList<TeaBookType> getAll() throws SQLException{
        return null;
    }

    @Override
    public boolean save(TeaBookType entity) throws SQLException{
        return SQLUtil.crudUtil("INSERT INTO tea_book_type_details VALUES(?,?,?,?,?)",
                entity.getTeaBookTypeId(),
                Date.valueOf(entity.getDate()),
                entity.getTypeId(),
                entity.getAmount(),
                entity.isConfirmed()
        );
    }

    @Override
    public boolean update(TeaBookType dto) throws SQLException{
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.crudUtil("DELETE FROM tea_book_type_details WHERE teaBookTypeId = ?",id);
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT teaBookTypeId FROM tea_book_type_details ORDER BY teaBookTypeId DESC LIMIT 1");
        String currentTeaBookTypeId = null;
        if (resultSet.next()){
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

    @Override
    public TeaBookType search(String id) throws SQLException {
        return null;
    }


    public List<TeaBookType> getAllTeaBookTypeDetails(String date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_book_type_details WHERE date=?",date);
        ArrayList<TeaBookType> teaBookTypeList = new ArrayList<>();
        while (resultSet.next()){
            teaBookTypeList.add(new TeaBookType(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getBoolean(5)
            ));
        }
        return teaBookTypeList;
    }


    public boolean confirmTeaBook(LocalDate date) throws SQLException{
        return  SQLUtil.crudUtil("UPDATE tea_book_type_details SET isConfirmed= true WHERE date=?",date);
    }

    public List<TeaBookTypeDetails> getTotalAmount(LocalDate date) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT typeId,SUM(amount) FROM tea_book_type_details WHERE date=? AND isConfirmed = false GROUP BY typeId",date);
        ArrayList<TeaBookTypeDetails> teaBookTypeDetailDtoList = new ArrayList<>();
        while (resultSet.next()){
            teaBookTypeDetailDtoList.add(new TeaBookTypeDetails(
                    resultSet.getString(1),
                    resultSet.getDouble(2)
            ));
        }
        return teaBookTypeDetailDtoList;
    }

}

