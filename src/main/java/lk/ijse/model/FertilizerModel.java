package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Fertilizer;
import lk.ijse.view.tdm.FertilizerSalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FertilizerModel {
    public String generateNextFertilizerId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT fertilizerId FROM fertilizer ORDER BY fertilizerId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

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

    public boolean addFertilizer(Fertilizer dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO fertilizer VALUES (?,?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getFertilizerId());
        pstm.setString(2,dto.getBrand());
        pstm.setString(3,dto.getDescription());
        pstm.setString(4,dto.getSize());
        pstm.setDouble(5,dto.getPrice());
        pstm.setInt(6,dto.getQty());

        return pstm.executeUpdate() > 0;

    }

    public List<Fertilizer> getAllFertilizers() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM fertilizer";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<Fertilizer> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()){

            String fertilizerId = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String description = resultSet.getString(3);
            String size = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            int qty = resultSet.getInt(6);

            Fertilizer dto = new Fertilizer(fertilizerId,brand,description,size,price,qty);

            dtoList.add(dto);

        }


        return dtoList;
    }

    public Fertilizer getFertilizer(String fertilizerId) throws SQLException {

        Connection connection =  DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM fertilizer WHERE fertilizerId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,fertilizerId);

        ResultSet resultSet = pstm.executeQuery();


        Fertilizer dto = null;

        if (resultSet.next()){
            String fertilizer_Id = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String description = resultSet.getString(3);
            String size = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            int qty = resultSet.getInt(6);

            dto = new Fertilizer(fertilizer_Id,brand,description,size,price,qty);
        }

        return dto;
    }

    public boolean deleteFertilizer(String fertilizerId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM fertilizer WHERE fertilizerId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,fertilizerId);

        return pstm.executeUpdate() > 0;

    }

    public boolean updateFertilizer(Fertilizer dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE fertilizer SET brand = ?, description = ?, size = ?,  unitPrice = ? , qty_on_hand = ?  WHERE fertilizerId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getBrand());
        pstm.setString(2,dto.getDescription());
        pstm.setString(3,dto.getSize());
        pstm.setDouble(4,dto.getPrice());
        pstm.setInt(5,dto.getQty());
        pstm.setString(6,dto.getFertilizerId());

        return pstm.executeUpdate() > 0;

    }

    public Fertilizer searchFertilizer(String fertilizerId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM fertilizer WHERE fertilizerId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,fertilizerId);

        ResultSet resultSet = pstm.executeQuery();

        Fertilizer dto = null;

        if (resultSet.next()){

            String fertilizer_Id = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String description = resultSet.getString(3);
            String size = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            int qty = resultSet.getInt(6);

            dto = new Fertilizer(fertilizer_Id,brand,description,size,price,qty);
        }
        return dto;

    }

    public boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException {
        for (FertilizerSalesCartTm cartTm: tmList){
            if (!updateQty(cartTm)){
                return false;
            }

        }
        return true;
    }

    private boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE fertilizer SET qty_on_hand = qty_on_hand - ? WHERE fertilizerId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1,cartTm.getQty());
        pstm.setString(2,cartTm.getFertilizerId());

        return pstm.executeUpdate() > 0;

    }
}

