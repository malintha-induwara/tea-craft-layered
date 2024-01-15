package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.dto.PackagingDto;
import lk.ijse.teacraft.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface PackagingBO extends SuperBO {
    List<PackagingDto> getAllPackaging(String teaType) throws SQLException;
    String getPackId(String teaTypeId, String packSize) throws SQLException;
    PackagingDto searchPackaging(String packId) throws SQLException;
    boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException;
    String getTypeId(String packId) throws SQLException;
    boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException;
    boolean updatePackagingQty(SalesCartTm tm) throws SQLException;
    List<PackagingDto> getAllPackaging() throws SQLException;
    String generateNextPackId() throws SQLException;
    boolean deletePackage(String packId) throws SQLException;
    boolean addPackage(PackagingDto dto) throws SQLException ;
    boolean updatePack(String packId, String typeId, String size, double price) throws SQLException;
}
