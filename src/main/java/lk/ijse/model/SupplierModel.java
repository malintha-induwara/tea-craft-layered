package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {


    public boolean saveSupplier(Supplier dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO tea_supplier VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSupId());
        pstm.setString(2, dto.getFirstName());
        pstm.setString(3, dto.getLastName());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getBank());
        pstm.setString(6, dto.getBankNo());
        pstm.setString(7, dto.getMobileNo());


        boolean isSaved = pstm.executeUpdate()>0;

        return  isSaved;

    }

    public String generateNextSupplierId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="SELECT supId FROM tea_supplier ORDER BY supId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentSupplierId = null;

        if (resultSet.next()){
            currentSupplierId = resultSet.getString(1);
            return splitSupplierId(currentSupplierId);
        }

        return splitSupplierId(currentSupplierId);

    }

    private String splitSupplierId(String currentSupplierId) {

      if (currentSupplierId!=null){
          String [] split = currentSupplierId.split("S");
          int selectedId = Integer.parseInt(split[1]);

          if (selectedId<9) {
              selectedId++;
              return "S00" + selectedId;
          }else if (selectedId<99) {
              selectedId++;
              return "S0" + selectedId;
          }else {
              selectedId++;
              return "S" + selectedId;
          }
      }
        return "S001";
    }

    public boolean deleteSupplier(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM tea_supplier WHERE supId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_supplier";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<Supplier> dtoList = new ArrayList<>();


        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()){

            String supId = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String address = resultSet.getString(4);
            String bank = resultSet.getString(5);
            String bankNo = resultSet.getString(6);
            String mobileNo = resultSet.getString(7);

            var dto = new Supplier(supId,firstName,lastName,address,bank,bankNo,mobileNo);
            dtoList.add(dto);
        }


        return dtoList;
    }

    public Supplier searchSupplier(String supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM tea_supplier WHERE supId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,supId);

        ResultSet resultSet = pstm.executeQuery();

        Supplier dto = null;

        if (resultSet.next()){
            String sup_Id = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String address = resultSet.getString(4);
            String bank = resultSet.getString(5);
            String bankNo = resultSet.getString(6);
            String mobileNo = resultSet.getString(7);

            dto = new Supplier(sup_Id,firstName,lastName,address,bank,bankNo,mobileNo);
        }


        return dto;
    }

    public boolean updateSupplier(Supplier supplierDto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tea_supplier SET firstName=?,lastName=?,address=?,bank=?,bankNo=?,mobileNo=? WHERE supId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,supplierDto.getFirstName());
        pstm.setString(2,supplierDto.getLastName());
        pstm.setString(3,supplierDto.getAddress());
        pstm.setString(4,supplierDto.getBank());
        pstm.setString(5,supplierDto.getBankNo());
        pstm.setString(6,supplierDto.getMobileNo());
        pstm.setString(7,supplierDto.getSupId());

        return pstm.executeUpdate()>0;

    }


    public String getSupplierName(String supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT firstName FROM tea_supplier WHERE supId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, supId);

        ResultSet resultSet = pstm.executeQuery();

        String name = null;

        if (resultSet.next()) {
            name = resultSet.getString(1);
        }

        return name;
    }
}
