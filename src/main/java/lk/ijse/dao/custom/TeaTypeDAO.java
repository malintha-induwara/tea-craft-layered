package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.TeaTypes;

import java.sql.SQLException;

public interface TeaTypeDAO extends CrudDAO<TeaTypes> {
    String getTeaTypeId(String type) throws SQLException;
}
