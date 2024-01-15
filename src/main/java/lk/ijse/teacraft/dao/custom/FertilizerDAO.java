package lk.ijse.teacraft.dao.custom;

import lk.ijse.teacraft.dao.CrudDAO;
import lk.ijse.teacraft.entity.Fertilizer;
import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface FertilizerDAO extends CrudDAO<Fertilizer> {
    boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException;
    boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException;
}
