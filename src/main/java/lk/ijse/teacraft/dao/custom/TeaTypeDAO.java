package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.entity.PackagingCountAmount;
import lk.ijse.teacraft.entity.TeaBookTypeDetails;
import lk.ijse.teacraft.entity.TeaTypes;

import java.sql.SQLException;
import java.util.List;

public interface TeaTypeDAO extends CrudDAO<TeaTypes> {
    String getTeaTypeId(String type) throws SQLException;
    String getTeaType(String typeId) throws SQLException;
    boolean updateTeaTypeAmount(TeaBookTypeDetails entity) throws SQLException;
    double getTeaAmount(String teaType) throws SQLException;
    boolean updateAmount(double amount,String typeId) throws SQLException ;

}
