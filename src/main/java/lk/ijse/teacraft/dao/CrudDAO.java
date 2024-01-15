package lk.ijse.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    ArrayList<T> getAll() throws SQLException;
    boolean save(T dto) throws SQLException ;
    boolean update(T dto) throws SQLException ;
    boolean exist(String id) throws SQLException;
    boolean delete(String id) throws SQLException ;
    String generateID() throws SQLException;
    T search(String id) throws SQLException;

}

