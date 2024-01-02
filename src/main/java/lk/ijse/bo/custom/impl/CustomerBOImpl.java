package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO =(CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = customerDAO.getAll();
        ArrayList<CustomerDto> allCustomerDto = new ArrayList<>();
        for (Customer customer : allCustomer) {
            allCustomerDto.add(new CustomerDto(
                    customer.getCusId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNo()
            ));
        }
        return allCustomerDto;
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(
                dto.getCusId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getMobileNo()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(
                dto.getCusId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getMobileNo()
        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateID();
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        if (customer!=null){
            return new CustomerDto(
                    customer.getCusId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNo()
            );
        }
        return null;
    }

    @Override
    public String searchCustomerID(String name) throws SQLException, ClassNotFoundException {
        return customerDAO.searchCustomerId(name);
    }
}

