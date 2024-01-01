package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Fertilizer;
import lk.ijse.view.tdm.FertilizerSalesCartTm;

import java.sql.SQLException;
import java.util.List;

public interface FertilizerDAO extends CrudDAO<Fertilizer> {
    boolean updateFertilizer(List<FertilizerSalesCartTm> tmList) throws SQLException, ClassNotFoundException;
    boolean updateQty(FertilizerSalesCartTm cartTm) throws SQLException, ClassNotFoundException;
}
