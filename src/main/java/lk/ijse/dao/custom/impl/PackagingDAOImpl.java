package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PackagingDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.Packaging;
import lk.ijse.util.SQLUtil;
import lk.ijse.view.tdm.SalesCartTm;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagingDAOImpl implements PackagingDAO {
    @Override
    public ArrayList<Packaging> getAll() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM packaging");
        ArrayList<Packaging> entityList = new ArrayList<>();
        while (resultSet.next()){
            entityList.add(new Packaging(
                    resultSet.getString("packId"),
                    resultSet.getString("typeId"),
                    resultSet.getString("description"),
                    resultSet.getInt("packageCount"),
                    resultSet.getDouble("price")
            ));
        }
        return entityList;
    }

    @Override
    public boolean save(Packaging entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO packaging VALUES(?,?,?,?,?)",
                entity.getPackId(),
                entity.getTypedId(),
                entity.getDescription(),
                entity.getPackageCount(),
                entity.getPrice());
    }

    @Override
    public boolean update(Packaging entity) throws SQLException{
        return SQLUtil.crudUtil("UPDATE packaging SET typeId=?,description=?,packageCount=?,price=? WHERE packId=?",
                entity.getTypedId(),
                entity.getDescription(),
                entity.getPackageCount(),
                entity.getPrice(),
                entity.getPackId());
    }

    @Override
    public boolean exist(String id) throws SQLException{
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return SQLUtil.crudUtil("DELETE FROM packaging WHERE packId=?",id);
    }

    @Override
    public String generateID() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT packId FROM packaging ORDER BY packId DESC LIMIT 1");
        String currentPackId = null;
        if (resultSet.next()){
            currentPackId = resultSet.getString(1);
            return splitPackId(currentPackId);
        }
        return splitPackId(currentPackId);
    }


    private String splitPackId(String currentPackId) {
        if (currentPackId != null) {
            String[] split = currentPackId.split("P");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId < 9) {
                selectedId++;
                return "P00" + selectedId;
            } else if (selectedId < 99) {
                selectedId++;
                return "P0" + selectedId;
            } else {
                selectedId++;
                return "P" + selectedId;
            }
        }
        return "P001";
    }

    @Override
    public Packaging search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM packaging WHERE packId=?",id);
        if (resultSet.next()){
            return new Packaging(
                    resultSet.getString("packId"),
                    resultSet.getString("typeId"),
                    resultSet.getString("description"),
                    resultSet.getInt("packageCount"),
                    resultSet.getDouble("price")
            );
        }
        return null;
    }

    public List<Packaging> getAllPackaging(String teaType) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM packaging WHERE typeId=?",teaType);
        List<Packaging> packagingList = new ArrayList<>();
        while (resultSet.next()){
            Packaging packaging = new Packaging(
                    resultSet.getString("packId"),
                    resultSet.getString("typeId"),
                    resultSet.getString("description"),
                    resultSet.getInt("packageCount"),
                    resultSet.getDouble("price")
            );
            packagingList.add(packaging);
        }
        return packagingList;
    }

    public String getPackId(String teaTypeId, String packSize) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT packId FROM packaging WHERE typeId=? AND description=?",teaTypeId,packSize);
        resultSet.next();
        return resultSet.getString(1);
    }


    public boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException {
        for (PackagingCountAmountDto dto : dtoList) {
            boolean isUpdated = SQLUtil.crudUtil("UPDATE packaging SET packageCount= packageCount + ? WHERE packId=?",dto.getCount(),dto.getPackId());
            if (!isUpdated){
                return false;
            }
        }
        return true;
    }

    public boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException{
        for (SalesCartTm tm : tmList) {
            if (!updatePackagingQty(tm)){
                return false;
            }
        }
        return true;
    }

    public boolean updatePackagingQty(SalesCartTm tm) throws SQLException {
        return SQLUtil.crudUtil("UPDATE packaging SET packageCount=packageCount-? WHERE packId=?",tm.getQty(),tm.getPackId());
    }

    public String getTypeId(String packId) throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT typeId FROM packaging WHERE packId=?",packId);
        resultSet.next();
        return resultSet.getString(1);
    }

    public boolean updatePack(String packId, String typeId, String size, double price) throws SQLException{
        return SQLUtil.crudUtil("UPDATE packaging SET typeId=?,description=?,price=? WHERE packId=?",typeId,size,price,packId);
    }


}

