package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.FertilizerBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.FertilizerDAO;
import lk.ijse.teacraft.dto.FertilizerDto;
import lk.ijse.teacraft.entity.Fertilizer;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FertilizerBOImpl implements FertilizerBO {

    private final FertilizerDAO fertilizerDAO = (FertilizerDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FERTILIZER);

    @Override
    public String generateNextFertilizerId() throws SQLException {
        return fertilizerDAO.generateID();
    }
    @Override
    public boolean addFertilizer(FertilizerDto dto) throws SQLException {
        return fertilizerDAO.save(new Fertilizer(
                dto.getFertilizerId(),
                dto.getBrand(),
                dto.getDescription(),
                dto.getSize(),
                dto.getPrice(),
                dto.getQty()));
    }
    @Override
    public List<FertilizerDto> getAllFertilizers() throws SQLException {
        List<Fertilizer> fertilizerList = fertilizerDAO.getAll();
        List<FertilizerDto> dtoList = new ArrayList<>();
        for (Fertilizer fertilizer : fertilizerList) {
            dtoList.add(new FertilizerDto(
                    fertilizer.getFertilizerId(),
                    fertilizer.getBrand(),
                    fertilizer.getDescription(),
                    fertilizer.getSize(),
                    fertilizer.getPrice(),
                    fertilizer.getQty()
            ));
        }
        return dtoList;
    }
    @Override
    public FertilizerDto getFertilizer(String fertilizerId) throws SQLException {
        Fertilizer fertilizer = fertilizerDAO.search(fertilizerId);
        if (fertilizer!=null){
            return new FertilizerDto(
                    fertilizer.getFertilizerId(),
                    fertilizer.getBrand(),
                    fertilizer.getDescription(),
                    fertilizer.getSize(),
                    fertilizer.getPrice(),
                    fertilizer.getQty()
            );
        }
        return null;
    }
    @Override
    public boolean deleteFertilizer(String fertilizerId) throws SQLException {
        return fertilizerDAO.delete(fertilizerId);
    }
    @Override
    public boolean updateFertilizer(FertilizerDto dto) throws SQLException {
        return fertilizerDAO.update(new Fertilizer(
                dto.getFertilizerId(),
                dto.getBrand(),
                dto.getDescription(),
                dto.getSize(),
                dto.getPrice(),
                dto.getQty()
        ));
    }
    @Override
    public FertilizerDto searchFertilizer(String fertilizerId) throws SQLException {

        Fertilizer fertilizer = fertilizerDAO.search(fertilizerId);
        if (fertilizer!=null){
            return new FertilizerDto(
                    fertilizer.getFertilizerId(),
                    fertilizer.getBrand(),
                    fertilizer.getDescription(),
                    fertilizer.getSize(),
                    fertilizer.getPrice(),
                    fertilizer.getQty()
            );
        }
        return null;
    }
    @Override
    public boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException {
        return fertilizerDAO.updateFertilizer(tmList);
    }
    @Override
    public boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException {
        return fertilizerDAO.updateQty(cartTm);
    }
}

