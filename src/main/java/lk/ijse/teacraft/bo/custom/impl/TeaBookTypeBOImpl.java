package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.TeaBookTypeBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.TeaBookTypeDAO;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.dto.TeaBookTypeDto;
import lk.ijse.teacraft.entity.TeaBookType;
import lk.ijse.teacraft.entity.TeaBookTypeDetails;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeaBookTypeBOImpl implements TeaBookTypeBO {


    TeaBookTypeDAO teaBookTypeDAO = (TeaBookTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_BOOK_TYPE);

    @Override
    public String generateNextTeaBookTypeId() throws SQLException {
        return teaBookTypeDAO.generateID();
    }

    @Override
    public boolean saveTeaBookType(TeaBookTypeDto dto) throws SQLException {
        return teaBookTypeDAO.save(new TeaBookType(
                dto.getTeaBookTypeId(),
                dto.getDate(),
                dto.getTypeId(),
                dto.getAmount(),
                dto.isConfirmed()
        ));
    }

    @Override
    public List<TeaBookTypeDto> getAllTeaBookTypeDetails(String date) throws SQLException {

        List<TeaBookType> allTeaBookTypeDetails = teaBookTypeDAO.getAllTeaBookTypeDetails(date);
        List<TeaBookTypeDto> teaBookTypeDtoList = new ArrayList<>();
        for (TeaBookType teaBookType : allTeaBookTypeDetails) {
            teaBookTypeDtoList.add(new TeaBookTypeDto(
                    teaBookType.getTeaBookTypeId(),
                    teaBookType.getDate(),
                    teaBookType.getTypeId(),
                    teaBookType.getAmount(),
                    teaBookType.isConfirmed()
            ));
        }

        return teaBookTypeDtoList;
    }

    @Override
    public boolean deleteTeaBookType(String teaBookTypeId) throws SQLException {
        return teaBookTypeDAO.delete(teaBookTypeId);
    }

    @Override
    public boolean confirmTeaBook(LocalDate date) throws SQLException {
        return teaBookTypeDAO.confirmTeaBook(date);
    }

    @Override
    public List<TeaBookTypeDetailDto> getTotalAmount(LocalDate date) throws SQLException {
        List<TeaBookTypeDetails> totalAmount = teaBookTypeDAO.getTotalAmount(date);
        List<TeaBookTypeDetailDto> teaBookTypeDetailDtoList = new ArrayList<>();
        for (TeaBookTypeDetails teaBookTypeDetails : totalAmount) {
            teaBookTypeDetailDtoList.add(new TeaBookTypeDetailDto(
                    teaBookTypeDetails.getTypeId(),
                    teaBookTypeDetails.getAmount()
            ));
        }
        return teaBookTypeDetailDtoList;
    }
}

