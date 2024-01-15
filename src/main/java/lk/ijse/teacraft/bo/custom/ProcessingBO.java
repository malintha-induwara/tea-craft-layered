package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.TeaBookTypeDetailDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ProcessingBO extends SuperBO {
    boolean updateDetails(LocalDate date, List<TeaBookTypeDetailDto> dtoList) throws SQLException;
}
