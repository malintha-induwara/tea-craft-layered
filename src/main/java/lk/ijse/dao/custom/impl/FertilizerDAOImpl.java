package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.FertilizerDAO;
import lk.ijse.entity.Fertilizer;
import lk.ijse.util.SQLUtil;
import lk.ijse.view.tdm.FertilizerSalesCartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FertilizerDAOImpl implements FertilizerDAO {
    @Override
    public ArrayList<Fertilizer> getAll() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM fertilizer");
        ArrayList<Fertilizer> dtoList = new ArrayList<>();
        while (resultSet.next()){
            Fertilizer dto = new Fertilizer(resultSet.getString("fertilizerId"),
                    resultSet.getString("brand"),
                    resultSet.getString("description"),
                    resultSet.getString("size"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getInt("qty_on_hand"));
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public boolean save(Fertilizer entity) throws SQLException{
        return SQLUtil.crudUtil("INSERT INTO fertilizer VALUES (?,?,?,?,?,?)",entity.getFertilizerId(),entity.getBrand(),entity.getDescription(),entity.getSize(),entity.getPrice(),entity.getQty());
    }

    @Override
    public boolean update(Fertilizer entity) throws SQLException {
        return SQLUtil.crudUtil("UPDATE fertilizer SET brand = ?, description = ?, size = ?,  unitPrice = ? , qty_on_hand = ?  WHERE fertilizerId = ?",entity.getBrand(),entity.getDescription(),entity.getSize(),entity.getPrice(),entity.getQty(),entity.getFertilizerId());
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return SQLUtil.crudUtil("DELETE FROM fertilizer WHERE fertilizerId = ?",id);
    }

    @Override
    public String generateID() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT fertilizerId FROM fertilizer ORDER BY fertilizerId DESC LIMIT 1");
        String currentFertilizerId = null;
        if(resultSet.next()){
            currentFertilizerId = resultSet.getString(1);
            return splitFertilizerId(currentFertilizerId);
        }
        return splitFertilizerId(currentFertilizerId);
    }

    private String splitFertilizerId(String currentFertilizerId) {
        if (currentFertilizerId != null){
            String[] split = currentFertilizerId.split("F");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9){
                selectedId++;
                return "F00"+selectedId;
            }else if (selectedId < 99){
                selectedId++;
                return "F0"+selectedId;
            }else {
                selectedId++;
                return "F"+selectedId;
            }

        }
        return "F001";
    }

    @Override
    public Fertilizer search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM fertilizer WHERE fertilizerId = ?",id);
        Fertilizer entity = null;
        if (resultSet.next()){
            entity = new Fertilizer(resultSet.getString("fertilizerId"),
                    resultSet.getString("brand"),
                    resultSet.getString("description"),
                    resultSet.getString("size"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getInt("qty_on_hand")
                    );
        }
        return entity;
    }

    @Override
    public boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException {
        for (FertilizerSalesCartTm cartTm: tmList){
            if (!updateQty(cartTm)){
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException {
        return SQLUtil.crudUtil("UPDATE fertilizer SET qty_on_hand = qty_on_hand - ? WHERE fertilizerId = ?",cartTm.getQty(),cartTm.getFertilizerId());
    }
}

