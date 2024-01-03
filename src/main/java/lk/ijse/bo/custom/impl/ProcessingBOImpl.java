package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProcessingBO;
import lk.ijse.dto.TeaBookTypeDetailDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProcessingBOImpl implements ProcessingBO {









    @Override
    public boolean updateDetails(LocalDate date, List<TeaBookTypeDetailDto> dtoList) throws SQLException {
        return false;
    }
}

