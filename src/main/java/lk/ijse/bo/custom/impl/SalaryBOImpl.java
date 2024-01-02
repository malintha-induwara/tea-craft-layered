package lk.ijse.bo.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.dao.custom.SalaryDAO;
import lk.ijse.dto.SalaryDto;
import lk.ijse.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SALARY);

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
}

