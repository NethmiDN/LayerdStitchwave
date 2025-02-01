package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.PaymentBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.PaymentDAO;
import com.example.stitchwave.dto.PaymentDTO;
import com.example.stitchwave.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public boolean savePayment(PaymentDTO DTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(DTO.getPayment_id(), DTO.getAmount(), DTO.getDate());
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(PaymentDTO DTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(DTO.getPayment_id(), DTO.getAmount(), DTO.getDate());
        return paymentDAO.update(payment);
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        String nextId = paymentDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("P%03d", id);
        } else {
            return "P001";
        }
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = (ArrayList<Payment>) paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPayment_id(payment.getPayment_id());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setDate(payment.getDate());
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    @Override
    public ArrayList<String> getAllPaymentIds() throws SQLException, ClassNotFoundException {
        return paymentDAO.getAllIds();
    }

    @Override
    public Payment findByPaymentId(String selectedId) throws SQLException, ClassNotFoundException {
        return paymentDAO.findById(selectedId);
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        paymentDAO.delete(id);
        return false;
    }

}
