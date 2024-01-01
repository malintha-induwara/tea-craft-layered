package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.Packaging;
import lk.ijse.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface PackagingDAO extends CrudDAO<Packaging> {
    String getTypeId(String packId) throws SQLException, ClassNotFoundException;
    boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException, ClassNotFoundException;
    boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException, ClassNotFoundException;
    String getPackId(String teaTypeId, String packSize) throws SQLException, ClassNotFoundException;
    List<Packaging> getAllPackaging(String teaType) throws SQLException, ClassNotFoundException;
}
