package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.entity.PackagingCountAmount;
import lk.ijse.teacraft.entity.PackagingDetails;
import lk.ijse.teacraft.util.SQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PackagingDetailsDAO extends CrudDAO<PackagingDetails> {
    public double getTotalDecreasedAmount(String teaTypeId) throws SQLException ;
    public List<PackagingDetails> loadAllPackagingDetails(LocalDate date) throws SQLException;
    public List<PackagingCountAmount> getTotalCountAmount(LocalDate date) throws SQLException;
    public boolean confirmPackaging(LocalDate parse) throws SQLException;
}
