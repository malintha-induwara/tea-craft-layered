package lk.ijse.teacraft.bo.custom;
import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.SupplierDto;
import lk.ijse.teacraft.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDto dto) throws SQLException;
    String generateNextSupplierId() throws SQLException;
    boolean deleteSupplier(String id) throws SQLException;
    List<SupplierDto> getAllSuppliers() throws SQLException;
    SupplierDto searchSupplier(String supId) throws SQLException;
    boolean updateSupplier(SupplierDto supplierDto) throws SQLException;
    String getSupplierName(String supId) throws SQLException;
}
