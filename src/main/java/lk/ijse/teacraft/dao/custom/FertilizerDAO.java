package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Fertilizer;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface FertilizerDAO extends CrudDAO<Fertilizer> {
    boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException;
    boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException;
}
