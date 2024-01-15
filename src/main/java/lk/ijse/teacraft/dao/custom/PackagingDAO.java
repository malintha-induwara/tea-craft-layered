package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.Packaging;
import lk.ijse.teacraft.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface PackagingDAO extends CrudDAO<Packaging> {
    String getTypeId(String packId) throws SQLException;
    boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException;
    boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException;
    boolean updatePackagingQty(SalesCartTm tm) throws SQLException;
    String getPackId(String teaTypeId, String packSize) throws SQLException;
    List<Packaging> getAllPackaging(String teaType) throws SQLException;
    boolean updatePack(String packId, String typeId, String size, double price) throws SQLException;
}
