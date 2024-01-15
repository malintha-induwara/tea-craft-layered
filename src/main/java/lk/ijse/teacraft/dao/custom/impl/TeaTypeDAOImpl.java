package lk.ijse.dao.custom.impl;


import lk.ijse.dao.custom.TeaTypeDAO;
import lk.ijse.entity.TeaBookTypeDetails;
import lk.ijse.entity.TeaTypes;
import lk.ijse.util.SQLUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TeaTypeDAOImpl implements TeaTypeDAO {


    @Override
    public ArrayList<TeaTypes> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_types");
        ArrayList<TeaTypes> dtoList = new ArrayList<>();
        while(resultSet.next()) {
            TeaTypes entity = new TeaTypes(resultSet.getString("typeId"),resultSet.getString("type"),resultSet.getDouble("amount"));
            dtoList.add(entity);
        }
        return  dtoList;
    }

    @Override
    public boolean save(TeaTypes dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TeaTypes dto) throws SQLException{
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException{
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return false;
    }

    @Override
    public String generateID() throws SQLException {
        return null;
    }

    @Override
    public TeaTypes search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getTeaTypeId(String type) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT typeId FROM tea_types WHERE type=?",type);
        resultSet.next();
        return resultSet.getString(1);
    }

    public String getTeaType(String typeId) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT type FROM tea_types WHERE typeId=?",typeId);
        resultSet.next();
        return resultSet.getString(1);
    }

    @Override
    public boolean updateTeaTypeAmount(TeaBookTypeDetails entity) throws SQLException {
            return SQLUtil.crudUtil("UPDATE tea_types SET amount=? WHERE typeId=?", entity.getAmount(), entity.getTypeId());
    }

    @Override
    public double getTeaAmount(String teaType) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT amount FROM tea_types WHERE type=?",teaType);
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }

    @Override
    public boolean updateAmount(double amount,String typeId) throws SQLException {
        return SQLUtil.crudUtil("UPDATE tea_types SET amount = amount - ? WHERE typeId = ?", amount, typeId);
    }

}

