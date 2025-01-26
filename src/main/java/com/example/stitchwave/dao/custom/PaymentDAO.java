package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {
    public boolean delete(String selectedId) throws SQLException, ClassNotFoundException ;

}
