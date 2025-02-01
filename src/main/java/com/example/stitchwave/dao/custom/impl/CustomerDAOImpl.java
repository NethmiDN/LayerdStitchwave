package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.CustomerDAO;
import com.example.stitchwave.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString("customer_id");
        }
        return null;
    }

    public boolean save(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES (?,?,?)", customerDTO.getCustomer_id(), customerDTO.getName(), customerDTO.getContact());
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");

        ArrayList<Customer> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
            customerDTOS.add(customer);
        }
        return customerDTOS;
    }

    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name=?, contact=? WHERE customer_id=?", customerDTO.getName(), customerDTO.getContact(), customerDTO.getCustomer_id());
    }

    public boolean delete(String customer_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id=?", customer_id);
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM customer");

        ArrayList<String> customer_ids = new ArrayList<>();

        while (rst.next()) {
            customer_ids.add(rst.getString("customer_id"));
        }
        return customer_ids;
    }

    public Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?", selectedCusId);
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
        }
        return null;
    }
}