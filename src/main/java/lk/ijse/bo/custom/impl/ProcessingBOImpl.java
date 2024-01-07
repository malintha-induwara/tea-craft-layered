package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProcessingBO;
import lk.ijse.bo.custom.TeaBookTypeBO;
import lk.ijse.bo.custom.TeaTypeBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.util.TransactionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProcessingBOImpl implements ProcessingBO {

    private final TeaBookTypeBO teaBookTypeBO = (TeaBookTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_BOOK_TYPE);
    private final TeaTypeBO teaTypeBO = (TeaTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_TYPE);


    @Override
    public boolean updateDetails(LocalDate date, List<TeaBookTypeDetailDto> dtoList) throws SQLException {

        boolean result = false;
        try{
            TransactionUtil.autoCommitFalse();
            boolean isConfirmed = teaBookTypeBO.confirmTeaBook(date);

            if (isConfirmed){
                boolean isUpdated = teaTypeBO.updateTeaTypeAmount(dtoList);

                if (isUpdated){
                    TransactionUtil.commit();
                    result = true;
                }
            }
        }catch (SQLException e){
            TransactionUtil.rollback();
        }

        return result;
    }
}

