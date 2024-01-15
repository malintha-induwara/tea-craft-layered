package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.BOFactory;
import lk.ijse.teacraft.bo.custom.ProcessingBO;
import lk.ijse.teacraft.bo.custom.TeaBookTypeBO;
import lk.ijse.teacraft.bo.custom.TeaTypeBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.TeaBookTypeDAO;
import lk.ijse.teacraft.dao.custom.TeaTypeDAO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.entity.TeaBookTypeDetails;
import lk.ijse.teacraft.util.TransactionUtil;

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

