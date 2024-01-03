package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.dto.TeaTypesDto;
import lk.ijse.entity.TeaTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeaTypeBO extends SuperBO {

    List<TeaTypesDto> getAllTeaTypes() throws SQLException;

    String getTeaTypeId(String type) throws SQLException;

    String getTeaType(String typeId) throws SQLException;

    boolean updateTeaTypeAmount(List<TeaBookTypeDetailDto> dtoList) throws SQLException;

    double getTeaAmount(String teaType) throws SQLException;

    boolean updateAmount(List<PackagingCountAmountDto> dtoList) throws SQLException;

}
