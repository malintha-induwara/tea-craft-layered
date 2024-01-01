package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.Packaging;
import lk.ijse.view.tdm.SalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagingModel {

    public List<Packaging> getAllPackaging(String teaType) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packaging WHERE typeId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,teaType);


        List<Packaging> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        Packaging dto = null;

        while (resultSet.next()){

            String packId = resultSet.getString(1);
            String typeId = resultSet.getString(2);
            String description = resultSet.getString(3);
            int count= resultSet.getInt(4);
            double price = resultSet.getDouble(5);

            dto = new Packaging(packId,typeId,description,count,price);

            dtoList.add(dto);
        }

        return dtoList;
    }

    public String getPackId(String teaTypeId, String packSize) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT packId FROM packaging WHERE typeId=? AND description=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,teaTypeId);
        pstm.setString(2,packSize);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getString(1);

    }

    public Packaging searchPackaging(String packId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packaging WHERE packId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,packId);

        ResultSet resultSet = pstm.executeQuery();

        Packaging dto = null;

        if (resultSet.next()){

            String id = resultSet.getString(1);
            String typeId = resultSet.getString(2);
            String description = resultSet.getString(3);
            int count = resultSet.getInt(4);
            double price = resultSet.getDouble(5);

            dto = new Packaging(id,typeId,description,count,price);

        }

        return dto;
    }

    public boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packaging SET packageCount= packageCount + ? WHERE packId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        for (PackagingCountAmountDto dto : dtoList) {

            pstm.setInt(1,dto.getCount());
            pstm.setString(2,dto.getPackId());

            if (pstm.executeUpdate()<=0){
                return false;
            }
        }

        return true;
    }

    public String getTypeId(String packId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT typeId FROM packaging WHERE packId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,packId);

        ResultSet resultSet = pstm.executeQuery();

        resultSet.next();

        return resultSet.getString(1);

    }

    public boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException {
        for (SalesCartTm tm : tmList) {
            if (!updatePackagingQty(tm)){
                return false;
            }
        }
        return true;
    }

    private boolean updatePackagingQty(SalesCartTm tm) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packaging SET packageCount = packageCount - ? WHERE packId = ?";



        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1,tm.getQty());
        pstm.setString(2, tm.getPackId());

        return pstm.executeUpdate()>0;

    }

    public List<Packaging> getAllPackaging() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM packaging";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Packaging> dtoList = new ArrayList<>();

        Packaging dto = null;

        while (resultSet.next()){

            String packId = resultSet.getString(1);
            String typeId = resultSet.getString(2);
            String description = resultSet.getString(3);
            int count = resultSet.getInt(4);
            double price = resultSet.getDouble(5);

            dto = new Packaging(packId,typeId,description,count,price);

            dtoList.add(dto);
        }

        return dtoList;

    }

    public String generateNextPackId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT packId FROM packaging ORDER BY packId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

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

    public boolean deletePackage(String packId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM packaging WHERE packId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,packId);

        return pstm.executeUpdate()>0;
    }

    public boolean addPackage(Packaging dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO packaging VALUES (?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getPackId());
        pstm.setString(2,dto.getTypedId());
        pstm.setString(3,dto.getDescription());
        pstm.setInt(4,dto.getPackageCount());
        pstm.setDouble(5,dto.getPrice());

        return pstm.executeUpdate()>0;
    }

    public boolean updatePack(String packId, String typeId, String size, double price) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE packaging SET typeId=?, description=?, price=? WHERE packId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,typeId);
        pstm.setString(2,size);
        pstm.setDouble(3,price);
        pstm.setString(4,packId);

        return pstm.executeUpdate()>0;

    }
}
