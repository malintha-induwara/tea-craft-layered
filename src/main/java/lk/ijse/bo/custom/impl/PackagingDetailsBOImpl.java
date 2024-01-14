package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PackagingDetailsBO;
import lk.ijse.bo.custom.TeaTypeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PackagingDAO;
import lk.ijse.dao.custom.PackagingDetailsDAO;
import lk.ijse.dao.custom.TeaTypeDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.PackagingCountAmountDto;
import lk.ijse.dto.PackagingDetailsDto;
import lk.ijse.entity.PackagingCountAmount;
import lk.ijse.entity.PackagingDetails;
import lk.ijse.util.TransactionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PackagingDetailsBOImpl implements PackagingDetailsBO {

    private final PackagingDetailsDAO packagingDetailsDAO = (PackagingDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING_DETAILS);
    private final PackagingDAO packagingDAO = (PackagingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING);
    private final TeaTypeBO teaTypeDAO = (TeaTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_TYPE);


    @Override
    public String generateNextPackingId() throws SQLException {
        return packagingDetailsDAO.generateID();
    }

    @Override
    public boolean addPackagingDetails(PackagingDetailsDto dto) throws SQLException {
        return packagingDetailsDAO.save(new PackagingDetails(
                dto.getPackagingDetailsId(),
                dto.getDate(),
                dto.getPackId(),
                dto.getCount(),
                dto.getAmount(),
                dto.isConfirmed()));
    }

    @Override
    public double getTotalDecreasedAmount(String teaTypeId) throws SQLException {
        return packagingDetailsDAO.getTotalDecreasedAmount(teaTypeId);
    }

    @Override
    public List<PackagingDetailsDto> loadAllPackagingDetails(LocalDate date) throws SQLException {

        List<PackagingDetails> allPackagingDetails = packagingDetailsDAO.loadAllPackagingDetails(date);
        List<PackagingDetailsDto> packagingDetailsDtoList = new ArrayList<>();

        for (PackagingDetails packagingDetails : allPackagingDetails){
            packagingDetailsDtoList.add(new PackagingDetailsDto(
                    packagingDetails.getPackagingDetailsId(),
                    packagingDetails.getDate(),
                    packagingDetails.getPackId(),
                    packagingDetails.getCount(),
                    packagingDetails.getAmount(),
                    packagingDetails.isConfirmed()
            ));
        }

        return packagingDetailsDtoList;
    }

    @Override
    public boolean deletePackageDetails(String packageDetailsId) throws SQLException {
        return packagingDetailsDAO.delete(packageDetailsId);
    }

    @Override
    public List<PackagingCountAmountDto> getTotalCountAmount(LocalDate parse) throws SQLException {
        List<PackagingCountAmount> allTotalCountAmount = packagingDetailsDAO.getTotalCountAmount(parse);
        List<PackagingCountAmountDto> packagingCountAmountDtoList = new ArrayList<>();

        for (PackagingCountAmount packagingCountAmount : allTotalCountAmount){
            packagingCountAmountDtoList.add(new PackagingCountAmountDto(
                    packagingCountAmount.getPackId(),
                    packagingCountAmount.getCount(),
                    packagingCountAmount.getAmount()
            ));
        }

        return packagingCountAmountDtoList;
    }

    @Override
    public boolean confirmPackaging(LocalDate date) throws SQLException {
        return packagingDetailsDAO.confirmPackaging(date);
    }


    public boolean confirmPackaging(LocalDate date, List<PackagingCountAmountDto> dtoList) throws SQLException{

        boolean result = false;

        try{

            TransactionUtil.autoCommitFalse();

            boolean isConfirmed = confirmPackaging(date);

            if (isConfirmed){
                boolean isSaved = packagingDAO.updatePackagingCount(dtoList);
                if (isSaved){
                    boolean isUpdated = teaTypeDAO.updateAmount(dtoList);
                    if (isUpdated){
                        TransactionUtil.commit();
                        result = true;
                    }
                }
            }
        }catch (SQLException e){
            TransactionUtil.rollback();
        }

        return result;
    }

}

