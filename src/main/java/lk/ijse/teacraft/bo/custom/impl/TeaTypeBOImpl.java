package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.TeaTypeBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.PackagingDAO;
import lk.ijse.teacraft.dao.custom.TeaTypeDAO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.dto.TeaTypesDto;
import lk.ijse.teacraft.entity.PackagingCountAmount;
import lk.ijse.teacraft.entity.TeaBookTypeDetails;
import lk.ijse.teacraft.entity.TeaTypes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaTypeBOImpl implements TeaTypeBO {

    private final TeaTypeDAO teaTypeDAO = (TeaTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_TYPE);

    private final PackagingDAO packagingDAO = (PackagingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING);


    @Override
    public List<TeaTypesDto> getAllTeaTypes() throws SQLException {

        List<TeaTypes> allTeaTypes = teaTypeDAO.getAll();
        List<TeaTypesDto> teaTypesDtoList = new ArrayList<>();

        for (TeaTypes teaTypes : allTeaTypes) {
            teaTypesDtoList.add(new TeaTypesDto(
                    teaTypes.getTypeId(),
                    teaTypes.getType(),
                    teaTypes.getAmount()
            ));
        }

        return teaTypesDtoList;
    }

    @Override
    public String getTeaTypeId(String type) throws SQLException {
        return teaTypeDAO.getTeaTypeId(type);
    }

    @Override
    public String getTeaType(String typeId) throws SQLException {
        return teaTypeDAO.getTeaType(typeId);
    }


    @Override
    public double getTeaAmount(String teaType) throws SQLException {
        return teaTypeDAO.getTeaAmount(teaType);
    }

    @Override
    public boolean updateAmount(List<PackagingCountAmountDto> dtoList) throws SQLException {
        List<PackagingCountAmount> packagingCountAmountList = new ArrayList<>();
        for (PackagingCountAmountDto dto : dtoList) {
            packagingCountAmountList.add(new PackagingCountAmount(
                    dto.getPackId(),
                    dto.getCount(),
                    dto.getAmount()
            ));
        }

        for (PackagingCountAmount packagingCountAmount : packagingCountAmountList) {
            String typeId = packagingDAO.getTypeId(packagingCountAmount.getPackId());
            boolean isUpdated=teaTypeDAO.updateAmount(packagingCountAmount.getAmount(),typeId);
            if(!isUpdated){
                return false;
            }
        }
        return true;
    }
}

