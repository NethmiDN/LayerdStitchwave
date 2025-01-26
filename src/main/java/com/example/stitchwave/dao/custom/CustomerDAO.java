package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    public boolean delete(String selectedId) throws SQLException, ClassNotFoundException ;
}
