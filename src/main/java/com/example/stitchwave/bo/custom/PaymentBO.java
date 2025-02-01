package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.PaymentDTO;
import com.example.stitchwave.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO DTO) throws SQLException, ClassNotFoundException;

    boolean updatePayment(PaymentDTO DTO) throws SQLException, ClassNotFoundException;

    String getNextPaymentId() throws SQLException, ClassNotFoundException;

    ArrayList<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllPaymentIds() throws SQLException, ClassNotFoundException;

    Payment findByPaymentId(String selectedId) throws SQLException, ClassNotFoundException;

    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
}
