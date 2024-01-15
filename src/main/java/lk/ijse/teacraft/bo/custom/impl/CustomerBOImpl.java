package lk.ijse.teacraft.bo.custom.impl;

import lk.ijse.teacraft.bo.custom.CustomerBO;
import lk.ijse.teacraft.dao.DAOFactory;
import lk.ijse.teacraft.dao.custom.CustomerDAO;
import lk.ijse.teacraft.dto.CustomerDto;
import lk.ijse.teacraft.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO =(CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException{
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
    public boolean saveCustomer(CustomerDto dto) throws SQLException{
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
    public boolean updateCustomer(CustomerDto dto) throws SQLException{
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
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateCustomerID() throws SQLException {
        return customerDAO.generateID();
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException{
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
    public String searchCustomerID(String name) throws SQLException {
        return customerDAO.searchCustomerId(name);
    }
}

