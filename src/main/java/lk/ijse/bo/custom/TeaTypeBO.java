package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.dto.TeaTypesDto;
import java.sql.SQLException;
import java.util.List;

public interface TeaTypeBO extends SuperBO {
    List<TeaTypesDto> getAllTeaTypes() throws SQLException;
    String getTeaTypeId(String type) throws SQLException;
    String getTeaType(String typeId) throws SQLException;
    double getTeaAmount(String teaType) throws SQLException;
    boolean updateAmount(List<PackagingCountAmountDto> dtoList) throws SQLException;

}
