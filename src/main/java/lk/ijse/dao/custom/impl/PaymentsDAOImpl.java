package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PaymentsDAO;
import lk.ijse.entity.Payments;
import lk.ijse.util.SQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDAOImpl implements PaymentsDAO {
    @Override
    public ArrayList<Payments> getAll() throws SQLException{
        return null;
    }

    @Override
    public boolean save(Payments entity) throws SQLException {
        return SQLUtil.crudUtil("INSERT INTO payments VALUES(?,?,?,?,?)",
                entity.getPaymentId(),
                entity.getSupId(),
                entity.getAmount(),
                entity.getPayment(),
                Date.valueOf(entity.getDate())
        );
    }

    @Override
    public boolean update(Payments dto) throws SQLException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException{
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateID() throws SQLException{
        ResultSet resultSet = SQLUtil.crudUtil("SELECT paymentId FROM payments ORDER BY paymentId DESC LIMIT 1");
        String currentPaymentId = null;
        if (resultSet.next()) {
            currentPaymentId = resultSet.getString(1);
            return splitPaymentId(currentPaymentId);
        }
        return splitPaymentId(currentPaymentId);
    }

    private String splitPaymentId(String currentPaymentId) {
        if (currentPaymentId != null) {
            String[] split = currentPaymentId.split("P");
            int selectedId = Integer.parseInt(split[1]);
            if (selectedId < 9) {
                selectedId++;
                return "P00" + selectedId;
            } else if (selectedId < 99) {
                selectedId++;
                return "P0" + selectedId;
            } else {
                selectedId++;
                return "P" + selectedId;
            }
        }
        return "P001";
    }

    @Override
    public Payments search(String id) throws SQLException{
        return null;
    }

    public List<Payments> getAllPaymentsDetails(String supId) throws SQLException {
        ResultSet resultSet = SQLUtil.crudUtil("SELECT * FROM payments WHERE supId=?",supId);
        List<Payments> entityList = new ArrayList<>();
        while (resultSet.next()){
            Payments dto = new Payments(resultSet.getString("paymentId"),
                    resultSet.getString("supId"),
                    resultSet.getDouble("amount"),
                    resultSet.getDouble("payment"),
                    resultSet.getDate("date").toLocalDate());
            entityList.add(dto);
        }
        return entityList;
    }
}

