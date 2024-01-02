package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);


    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new Employee(
                dto.getEmpId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getSex(),
                dto.getDateOfBirth(),
                dto.getMobileNo()
        ));
    }

    @Override
    public String generateNextEmployeeId() throws SQLException {
        return employeeDAO.generateID();
    }

    @Override
    public boolean updateEmployee(EmployeeDto employee) throws SQLException {
        return employeeDAO.update(new Employee(
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAddress(),
                employee.getSex(),
                employee.getDateOfBirth(),
                employee.getMobileNo()
        ));
    }

    @Override
    public EmployeeDto searchEmployee(String empId) throws SQLException {
        Employee employee = employeeDAO.search(empId);
        return new EmployeeDto(
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAddress(),
                employee.getSex(),
                employee.getDateOfBirth(),
                employee.getMobileNo()
        );
    }

    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException {
        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(
                    employee.getEmpId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getAddress(),
                    employee.getSex(),
                    employee.getDateOfBirth(),
                    employee.getMobileNo()
            ));
        }
        return employeeDtos;
    }

    @Override
    public boolean deleteEmployee(String empId) throws SQLException {
        return employeeDAO.delete(empId);
    }
}

