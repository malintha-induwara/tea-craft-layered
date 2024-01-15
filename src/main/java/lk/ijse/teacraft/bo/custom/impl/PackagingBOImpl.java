package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.PackagingBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.PackagingDAO;
import lk.ijse.teacraft.dto.PackagingCountAmountDto;
import lk.ijse.teacraft.dto.PackagingDto;
import lk.ijse.teacraft.entity.Packaging;
import lk.ijse.teacraft.view.tdm.SalesCartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagingBOImpl implements PackagingBO{

    private final PackagingDAO packagingDAO = (PackagingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGING);


    @Override
    public List<PackagingDto> getAllPackaging(String teaType) throws SQLException {
        List<Packaging> packagingList = packagingDAO.getAllPackaging(teaType);
        List<PackagingDto> dtoList = new ArrayList<>();
        for (Packaging packaging : packagingList) {
            dtoList.add(new PackagingDto(
                    packaging.getPackId(),
                    packaging.getTypedId(),
                    packaging.getDescription(),
                    packaging.getPackageCount(),
                    packaging.getPrice()));
        }
        return dtoList;
    }

    @Override
    public String getPackId(String teaTypeId, String packSize) throws SQLException {
        return packagingDAO.getPackId(teaTypeId,packSize);
    }

    @Override
    public PackagingDto searchPackaging(String packId) throws SQLException {
        Packaging packaging = packagingDAO.search(packId);
        if (packaging!=null){
            return new PackagingDto(
                    packaging.getPackId(),
                    packaging.getTypedId(),
                    packaging.getDescription(),
                    packaging.getPackageCount(),
                    packaging.getPrice());
        }
        return null;
    }

    @Override
    public boolean updatePackagingCount(List<PackagingCountAmountDto> dtoList) throws SQLException {
        return packagingDAO.updatePackagingCount(dtoList);
    }

    @Override
    public String getTypeId(String packId) throws SQLException {
        return packagingDAO.getTypeId(packId);
    }

    @Override
    public boolean updatePackaging(List<SalesCartTm> tmList) throws SQLException {
        return packagingDAO.updatePackaging(tmList);
    }

    @Override
    public boolean updatePackagingQty(SalesCartTm tm) throws SQLException {
        return packagingDAO.updatePackagingQty(tm);
    }

    @Override
    public List<PackagingDto> getAllPackaging() throws SQLException {
        List<Packaging> packagingList = packagingDAO.getAll();
        List<PackagingDto> dtoList = new ArrayList<>();
        for (Packaging packaging : packagingList) {
            dtoList.add(new PackagingDto(
                    packaging.getPackId(),
                    packaging.getTypedId(),
                    packaging.getDescription(),
                    packaging.getPackageCount(),
                    packaging.getPrice()));
        }
        return dtoList ;
    }

    @Override
    public String generateNextPackId() throws SQLException {
        return packagingDAO.generateID();
    }

    @Override
    public boolean deletePackage(String packId) throws SQLException {
        return packagingDAO.delete(packId);
    }

    @Override
    public boolean addPackage(PackagingDto dto) throws SQLException {
        return packagingDAO.save(new Packaging(
                dto.getPackId(),
                dto.getTypedId(),
                dto.getDescription(),
                dto.getPackageCount(),
                dto.getPrice()));
    }

    @Override
    public boolean updatePack(String packId, String typeId, String size, double price) throws SQLException {
        return packagingDAO.updatePack(packId,typeId,size,price);
    }
}

