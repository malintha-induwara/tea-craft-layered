package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.TeaLeavesStockBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.TeaBookDAO;
import lk.ijse.teacraft.dao.custom.TeaLeavesStockDAO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.TeaLeavesStockDto;
import lk.ijse.teacraft.entity.TeaLeavesStock;
import lk.ijse.teacraft.util.TransactionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaLeavesStockBOImpl implements TeaLeavesStockBO {

    TeaLeavesStockDAO teaLeavesStockDAO = (TeaLeavesStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_LEAVES_STOCK);

    TeaBookDAO teaBookDAO = (TeaBookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TEA_BOOK);

    @Override
    public String generateNextTeaLeavesStockId() throws SQLException {
        return teaLeavesStockDAO.generateID();
    }

    @Override
    public boolean saveTeaLeavesStock(TeaLeavesStockDto dto) throws SQLException {
        return teaLeavesStockDAO.save(new TeaLeavesStock(
                dto.getTeaStockId(),
                dto.getSupId(),
                dto.getTeaBookId(),
                dto.getAmount(),
                dto.isPayed()
                ));
    }

    @Override
    public double getTotalAmount(String teaBookId) throws SQLException {
        return teaLeavesStockDAO.getTotalAmount(teaBookId);
    }

    @Override
    public List<TeaLeavesStockDto> getAllStockDetails(String dateBookId) throws SQLException {
        List<TeaLeavesStock> all = teaLeavesStockDAO.getAllStockDetails(dateBookId);
        List<TeaLeavesStockDto> dtoList = new ArrayList<>();
        for (TeaLeavesStock teaLeavesStock: all){
            dtoList.add(new TeaLeavesStockDto(
                    teaLeavesStock.getTeaStockId(),
                    teaLeavesStock.getSupId(),
                    teaLeavesStock.getTeaBookId(),
                    teaLeavesStock.getAmount(),
                    teaLeavesStock.isPayed()
            ));
        }
        return dtoList;
    }

    @Override
    public TeaLeavesStockDto searchTeaLeavesStock(String text) throws SQLException {
        TeaLeavesStock search = teaLeavesStockDAO.search(text);
        if (search!=null){
            return new TeaLeavesStockDto(
                    search.getTeaStockId(),
                    search.getSupId(),
                    search.getTeaBookId(),
                    search.getAmount(),
                    search.isPayed()
            );
        }
        return null;
    }

    @Override
    public boolean deleteTeaLeavesStock(String teaStockId) throws SQLException {
        return teaLeavesStockDAO.delete(teaStockId);
    }

    @Override
    public boolean updateTeaLeavesStock(TeaLeavesStockDto teaLeavesStockDto) throws SQLException {
        return teaLeavesStockDAO.update(new TeaLeavesStock(
                teaLeavesStockDto.getTeaStockId(),
                teaLeavesStockDto.getSupId(),
                teaLeavesStockDto.getTeaBookId(),
                teaLeavesStockDto.getAmount(),
                teaLeavesStockDto.isPayed()
        ));
    }
    @Override
    public double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException {
        return teaLeavesStockDAO.getTotalTeaLeavesSuppliedAmount(supId);
    }

    @Override
    public boolean updatePayedStatus(String supId) throws SQLException {
        return teaLeavesStockDAO.updatePayedStatus(supId);
    }


    public boolean addTeaLeavesStock(TeaLeavesStockDto teaLeavesStockDto, String teaBookId) throws SQLException {

        boolean result = false;


        try{
            TransactionUtil.autoCommitFalse();

            boolean isSaved = saveTeaLeavesStock(teaLeavesStockDto);

            if (isSaved){

                double dailyAmount = getTotalAmount(teaBookId);

                boolean isUpdated = teaBookDAO.updateTeaBookAmount(teaBookId,dailyAmount);

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

