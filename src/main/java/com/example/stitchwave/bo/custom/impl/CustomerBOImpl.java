package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.CustomerBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.CustomerDAO;
import com.example.stitchwave.dto.CustomerDTO;
import com.example.stitchwave.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO DTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(DTO.getCustomer_id(), DTO.getName(), DTO.getContact());
        return customerDAO.save(customer);
    }

    @Override
    public boolean updateCustomer(CustomerDTO DTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(DTO.getCustomer_id(), DTO.getName(), DTO.getContact());
        return customerDAO.update(customer);
    }

    @Override
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextId = customerDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("C%03d", id);
        } else {
            return "C001";
        }
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customer.getCustomer_id());
            customerDTO.setName(customer.getName());
            customerDTO.setContact(customer.getContact());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public ArrayList<Object> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAllIds();
        ArrayList<Object> customerIds = new ArrayList<>();
        for (Customer customer : customers) {
            customerIds.add(customer.getCustomer_id());
        }
        return customerIds;
    }

    @Override
    public Customer findByCustomerId(String selectedId) throws SQLException, ClassNotFoundException {
        return customerDAO.findById(selectedId);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        customerDAO.delete(id);
        return false;
    }

}
