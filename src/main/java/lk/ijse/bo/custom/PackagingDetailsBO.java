package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.PackagingDetailsDto;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PackagingDetailsBO extends SuperBO {
    String generateNextPackingId() throws SQLException;
    boolean addPackagingDetails(PackagingDetailsDto dto) throws SQLException;
    double getTotalDecreasedAmount(String teaTypeId) throws SQLException;
    List<PackagingDetailsDto> loadAllPackagingDetails(LocalDate date) throws SQLException;
    boolean deletePackageDetails(String packageDetailsId) throws SQLException;
    List<PackagingCountAmountDto> getTotalCountAmount(LocalDate parse) throws SQLException;
    boolean confirmPackaging(LocalDate parse) throws SQLException;
}
