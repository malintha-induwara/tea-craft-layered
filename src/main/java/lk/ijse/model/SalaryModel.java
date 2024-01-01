package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Salary;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean addSalary(Salary dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String query = "INSERT INTO salary VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setString(1,dto.getSalaryId());
        pstm.setString(2,dto.getEmpId());
        pstm.setDouble(3,dto.getAmount());
        pstm.setInt(4,dto.getDateCount());
        pstm.setDate(5, Date.valueOf(dto.getDate()));


        return pstm.executeUpdate()>0;

    }

    public String generateNextSalaryId() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT salaryId FROM salary ORDER BY salaryId DESC LIMIT 1";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String currentSalaryId = null;

        if (resultSet.next()){
            currentSalaryId = resultSet.getString(1);

            return splitSalaryId(currentSalaryId);
        }

        return splitSalaryId(currentSalaryId);
    }

    private String splitSalaryId(String currentSalaryId) {

        if (currentSalaryId != null){
            String[] split = currentSalaryId.split("S");
            int selectedId = Integer.parseInt(split[1]);

            if(selectedId<9){
                selectedId++;
                return "S00"+selectedId;
            }else if(selectedId<99){
                selectedId++;
                return "S0"+selectedId;
            }else{
                selectedId++;
                return "S"+selectedId;
            }
        }

        return "S001";
    }

    public List<Salary> getPaymentDetails(String supplierId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary WHERE empId=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,supplierId);

        List<Salary> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()){

            String salaryId = resultSet.getString(1);
            String empId = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            int dateCount = resultSet.getInt(4);
            LocalDate date = resultSet.getDate(5).toLocalDate();

            Salary dto = new Salary(salaryId,empId,amount,dateCount,date);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
