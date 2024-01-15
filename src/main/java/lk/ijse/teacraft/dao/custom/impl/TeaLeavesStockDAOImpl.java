package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TeaLeavesStockDAO;
import lk.ijse.entity.TeaLeavesStock;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaLeavesStockDAOImpl implements TeaLeavesStockDAO {
    @Override
    public ArrayList<TeaLeavesStock> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(TeaLeavesStock entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO tea_leaves_stock VALUES(?,?,?,?,?)",
                entity.getTeaStockId(),
                entity.getSupId(),
                entity.getTeaBookId(),
                entity.getAmount(),
                entity.isPayed());
    }

    @Override
    public boolean update(TeaLeavesStock entity) throws SQLException {
        return SQLUtil.crudUtil("UPDATE tea_leaves_stock SET supId=?,teaBookId=?,amount=? WHERE teaStockId=?",
                entity.getSupId(),
                entity.getTeaBookId(),
                entity.getAmount(),
                entity.getTeaStockId());
    }

    @Override
    public boolean exist(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.crudUtil("DELETE FROM tea_leaves_stock WHERE teaStockId=?",id);
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT teaStockId FROM tea_leaves_stock ORDER BY teaStockId DESC LIMIT 1");
        String currentTeaLeavesStockId = null;
        if (resultSet.next()){
            currentTeaLeavesStockId = resultSet.getString(1);
            return splitTeaLeavesStockId(currentTeaLeavesStockId);
        }
        return splitTeaLeavesStockId(currentTeaLeavesStockId);
    }

    private String splitTeaLeavesStockId(String currentTeaLeavesStockId) {
        if (currentTeaLeavesStockId!=null){
            String[] split = currentTeaLeavesStockId.split("TS");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9){
                selectedId++;
                return "TS00"+selectedId;
            }else if (selectedId < 99){
                selectedId++;
                return "TS0"+selectedId;
            }else {
                selectedId++;
                return "TS"+selectedId;
            }
        }
        return "TS001";
    }


    @Override
    public TeaLeavesStock search(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_leaves_stock WHERE teaStockId=?",id);
        if (resultSet.next()){
            return new TeaLeavesStock(
                    resultSet.getString("teaStockId"),
                    resultSet.getString("supId"),
                    resultSet.getString("teaBookId"),
                    resultSet.getDouble("amount"),
                    resultSet.getBoolean("isPayed")
            );
        }
        return null;
    }

    public boolean updatePayedStatus(String supId) throws SQLException {
        return SQLUtil.crudUtil("UPDATE tea_leaves_stock SET isPayed=true WHERE supId=?",supId);
    }

    public double getTotalTeaLeavesSuppliedAmount(String supId) throws SQLException {
        ResultSet resultSet =SQLUtil.crudUtil("SELECT SUM(amount) FROM tea_leaves_stock WHERE supId=? and isPayed=false",supId);
        if (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public List<TeaLeavesStock> getAllStockDetails(String dateBookId) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM tea_leaves_stock WHERE teaBookId=?",dateBookId);
        List<TeaLeavesStock> teaLeavesStocks = new ArrayList<>();
        while (resultSet.next()){
            teaLeavesStocks.add(new TeaLeavesStock(
                    resultSet.getString("teaStockId"),
                    resultSet.getString("supId"),
                    resultSet.getString("teaBookId"),
                    resultSet.getDouble("amount"),
                    resultSet.getBoolean("isPayed")
            ));
        }
        return teaLeavesStocks;
    }



    public double getTotalAmount(String teaBookId) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT SUM(amount) FROM tea_leaves_stock WHERE teaBookId=?",teaBookId);
        resultSet.next();
        return resultSet.getDouble(1);
    }

}

