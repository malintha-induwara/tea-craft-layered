package lk.ijse.dao.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PackagingDAO;
import lk.ijse.dao.custom.TeaTypeDAO;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.PackagingCountAmount;
import lk.ijse.entity.TeaBookTypeDetails;
import lk.ijse.entity.TeaTypes;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeDAOImpl implements TeaTypeDAO {

    PackagingDAO packagingDAO = (PackagingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING);


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
    public boolean updateTeaTypeAmount(List<TeaBookTypeDetails> dtoList) throws SQLException {
        for (TeaBookTypeDetails entity : dtoList) {
            boolean isUpdated = SQLUtil.crudUtil("UPDATE tea_types SET amount=? WHERE typeId=?", entity.getAmount(), entity.getTypeId());
            if (!isUpdated){
                return false;
            }
        }
        return true;
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
    public boolean updateAmount(List<PackagingCountAmount> dtoList) throws SQLException {
        for (PackagingCountAmount entity : dtoList) {
            String typeId = packagingDAO.getTypeId(entity.getPackId());
            boolean isUpdated = SQLUtil.crudUtil("UPDATE tea_types SET amount = amount - ? WHERE typeId = ?", entity.getAmount(), typeId);
            if (!isUpdated){
                return false;
            }
        }
        return true;
    }

}

