package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.PackagingDto;
import lk.ijse.entity.Packaging;
import lk.ijse.view.tdm.SalesCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PackagingBO extends SuperBO {
    public List<PackagingDto> getAllPackaging(String teaType) throws SQLException;
    public String getPackId(String teaTypeId, String packSize) throws SQLException;
    public PackagingDto searchPackaging(String packId) throws SQLException;
    public boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException;
    public String getTypeId(String packId) throws SQLException;
    public boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException;
    boolean updatePackagingQty(SalesCartTm tm) throws SQLException;
    List<PackagingDto> getAllPackaging() throws SQLException;
    String generateNextPackId() throws SQLException;
    boolean deletePackage(String packId) throws SQLException;
    boolean addPackage(PackagingDto dto) throws SQLException ;
    boolean updatePack(String packId, String typeId, String size, double price) throws SQLException;
}
