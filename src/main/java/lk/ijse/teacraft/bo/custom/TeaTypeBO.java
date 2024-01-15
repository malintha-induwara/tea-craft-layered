package lk.ijse.teacraft.bo.custom;

import lk.ijse.teacraft.bo.SuperBO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.dto.TeaTypesDto;
import java.sql.SQLException;
import java.util.List;

public interface TeaTypeBO extends SuperBO {
    List<TeaTypesDto> getAllTeaTypes() throws SQLException;
    String getTeaTypeId(String type) throws SQLException;
    String getTeaType(String typeId) throws SQLException;
    double getTeaAmount(String teaType) throws SQLException;
    boolean updateAmount(List<PackagingCountAmountDto> dtoList) throws SQLException;

}
