package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProcessingBO;
import lk.ijse.bo.custom.TeaBookTypeBO;
import lk.ijse.bo.custom.TeaTypeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.TeaBookTypeDAO;
import lk.ijse.dao.custom.TeaTypeDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.TeaBookTypeDetailDto;
import lk.ijse.entity.TeaBookTypeDetails;
import lk.ijse.util.TransactionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProcessingBOImpl implements ProcessingBO {


    private final TeaBookTypeDAO teaBookTypeDAO = (TeaBookTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_BOOK_TYPE);

    private final TeaTypeDAO teaTypeDAO = (TeaTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_TYPE);


    @Override
    public boolean updateDetails(LocalDate date, List<TeaBookTypeDetailDto> dtoList) throws SQLException {
        boolean result = false;
        try{
            TransactionUtil.autoCommitFalse();

            boolean isConfirmed = teaBookTypeDAO.confirmTeaBook(date);

            if (isConfirmed){
                boolean isUpdated = updateTeaTypeAmount(dtoList);

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

    public boolean updateTeaTypeAmount(List<TeaBookTypeDetailDto> dtoList) throws SQLException {
        List<TeaBookTypeDetails> teaBookTypeDetailsList = new ArrayList<>();
        for (TeaBookTypeDetailDto dto : dtoList) {
            teaBookTypeDetailsList.add(new TeaBookTypeDetails(
                    dto.getTypeId(),dto.getAmount()
            ));
        }
        for (TeaBookTypeDetails teaBookTypeDetails : teaBookTypeDetailsList) {
            boolean isUpdated=teaTypeDAO.updateTeaTypeAmount(teaBookTypeDetails);
            if(!isUpdated){
                return false;
            }
        }
        return true;
    }


}

