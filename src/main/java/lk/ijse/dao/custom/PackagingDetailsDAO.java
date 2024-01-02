package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.entity.PackagingDetails;
import lk.ijse.util.SQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PackagingDetailsDAO extends CrudDAO<PackagingDetails> {
    public double getTotalDecreasedAmount(String teaTypeId) throws SQLException, ClassNotFoundException ;
    public List<PackagingDetails> loadAllPackagingDetails(LocalDate date) throws SQLException, ClassNotFoundException;
    public List<PackagingCountAmountDto> getTotalCountAmount(LocalDate date) throws SQLException, ClassNotFoundException;
    public boolean confirmPackaging(LocalDate parse) throws SQLException, ClassNotFoundException;
}
