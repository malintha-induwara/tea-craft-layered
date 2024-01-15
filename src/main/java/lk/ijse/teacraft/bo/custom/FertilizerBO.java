package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.FertilizerDto;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface FertilizerBO extends SuperBO {
    String generateNextFertilizerId() throws SQLException;
    boolean addFertilizer(FertilizerDto dto) throws SQLException;
    List<FertilizerDto> getAllFertilizers() throws SQLException;
    FertilizerDto getFertilizer(String fertilizerId) throws SQLException;
    boolean deleteFertilizer(String fertilizerId) throws SQLException;
    boolean updateFertilizer(FertilizerDto dto) throws SQLException;
    FertilizerDto searchFertilizer(String fertilizerId) throws SQLException;
    boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException;
    boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException;
}
