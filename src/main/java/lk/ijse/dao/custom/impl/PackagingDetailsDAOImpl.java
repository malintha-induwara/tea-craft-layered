package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PackagingDetailsDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.PackagingDetails;
import lk.ijse.util.SQLUtil;
import lombok.Data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PackagingDetailsDAOImpl implements PackagingDetailsDAO {
    @Override
    public ArrayList<PackagingDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(PackagingDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("INSERT INTO packaging_details VALUES (?,?,?,?,?,?)",
                entity.getPackagingDetailsId(),
                Date.valueOf(entity.getDate()),
                entity.getPackId(),
                entity.getCount(),
                entity.getAmount(),
                entity.isConfirmed());
    }

    @Override
    public boolean update(PackagingDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("DELETE FROM packaging_details WHERE packagingDetailsId=?", id);
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.crudUtil("SELECT packagingDetailsId FROM packaging_details ORDER BY packagingDetailsId DESC LIMIT 1");

        String currentPackingId = null;

        if (resultSet.next()){
            currentPackingId = resultSet.getString(1);
            return splitPackingId(currentPackingId);
        }

        return splitPackingId(currentPackingId);
    }

    private String splitPackingId(String currentPackingId) {

        if (currentPackingId!=null){
            String [] split = currentPackingId.split("PD");
            int selectedId = Integer.parseInt(split[1]);

            if (selectedId<9) {
                selectedId++;
                return "PD00" + selectedId;
            }else if (selectedId<99) {
                selectedId++;
                return "PD0" + selectedId;
            }else {
                selectedId++;
                return "PD" + selectedId;
            }

        }
        return "PD001";

    }

    @Override
    public PackagingDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    public double getTotalDecreasedAmount(String teaTypeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT SUM(amount) FROM packaging_details WHERE packId IN (SELECT packId FROM packaging WHERE typeId=?) AND isConfirmed=0", teaTypeId);
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public List<PackagingDetails> loadAllPackagingDetails(LocalDate date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM packaging_details WHERE date=?", Date.valueOf(date));
        List<PackagingDetails> packagingDetailsList = new ArrayList<>();
        while (resultSet.next()){
            packagingDetailsList.add(new PackagingDetails(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getBoolean(6)
            ));
        }
        return packagingDetailsList;
    }


    public List<PackagingCountAmountDto> getTotalCountAmount(LocalDate date) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.crudUtil("SELECT packId,SUM(count),SUM(amount) FROM packaging_details WHERE date=? AND isConfirmed=0 GROUP BY packId", Date.valueOf(date));

        List<PackagingCountAmountDto> packagingCountAmountDtoList = new ArrayList<>();

        while (resultSet.next()){
            packagingCountAmountDtoList.add(new PackagingCountAmountDto(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getDouble(3)
            ));
        }

        return packagingCountAmountDtoList;
    }


    public boolean confirmPackaging(LocalDate parse) throws SQLException, ClassNotFoundException {
        return SQLUtil.crudUtil("UPDATE packaging_details SET isConfirmed=1 WHERE date=?", Date.valueOf(parse));
    }

}

