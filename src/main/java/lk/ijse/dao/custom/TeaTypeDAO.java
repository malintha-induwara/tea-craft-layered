package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.PackagingCountAmount;
import lk.ijse.entity.TeaBookTypeDetails;
import lk.ijse.entity.TeaTypes;

import java.sql.SQLException;
import java.util.List;

public interface TeaTypeDAO extends CrudDAO<TeaTypes> {
    String getTeaTypeId(String type) throws SQLException;
    String getTeaType(String typeId) throws SQLException;
    boolean updateTeaTypeAmount(TeaBookTypeDetails entity) throws SQLException;
    double getTeaAmount(String teaType) throws SQLException;
    boolean updateAmount(double amount,String typeId) throws SQLException ;

}
