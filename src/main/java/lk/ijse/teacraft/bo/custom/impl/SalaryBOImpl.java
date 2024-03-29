package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.BOFactory;
import lk.ijse.teacraft.bo.custom.SalaryBO;
import lk.ijse.teacraft.dao.custom.AttendanceDAO;
import lk.ijse.teacraft.dao.custom.SalaryDAO;
import lk.ijse.teacraft.db.DbConnection;
import lk.ijse.teacraft.dto.SalaryDto;
import lk.ijse.teacraft.entity.Salary;
import lk.ijse.teacraft.util.TransactionUtil;

import javax.sql.rowset.spi.TransactionalWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SALARY);
    AttendanceDAO attendanceDAO = (AttendanceDAO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ATTENDANCE);

    @Override
    public boolean addSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.save(new Salary(
                dto.getSalaryId(),
                dto.getEmpId(),
                dto.getAmount(),
                dto.getDateCount(),
                dto.getDate()
        ));
    }

    @Override
    public String generateNextSalaryId() throws SQLException {
        return salaryDAO.generateID();
    }

    @Override
    public List<SalaryDto> getPaymentDetails(String supplierId) throws SQLException {
        List<Salary> salaryList = salaryDAO.getPaymentDetails(supplierId);
        List<SalaryDto> dtoList = new ArrayList<>();
        for (Salary salary : salaryList) {
            dtoList.add(new SalaryDto(
                    salary.getSalaryId(),
                    salary.getEmpId(),
                    salary.getAmount(),
                    salary.getDateCount(),
                    salary.getDate()
            ));
        }
        return dtoList;
    }

    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        boolean result = false;

        try {
            TransactionUtil.autoCommitFalse();

            boolean isSalarySaved = addSalary(dto);

            if (isSalarySaved) {
                boolean isUpdated = attendanceDAO.updatePayedStatus(dto.getEmpId());

                if (isUpdated) {
                    TransactionUtil.commit();
                    result = true;
                }
            }
        } catch (SQLException e){
            TransactionUtil.rollback();
        }

        return result;
    }


}

