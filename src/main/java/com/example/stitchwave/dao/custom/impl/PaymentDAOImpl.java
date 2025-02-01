package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.PaymentDAO;
import com.example.stitchwave.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString("payment_id");
        }
        return null;
    }

    public boolean save(Payment paymentDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment VALUES (?,?,?)", paymentDTO.getPayment_id(), paymentDTO.getAmount(), paymentDTO.getDate());
    }

    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment");

        ArrayList<Payment> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString(1),  // customer ID
                    rst.getDouble(2),  // Name
                    rst.getDate(3).toLocalDate()  // Contact
            );
            paymentDTOS.add(payment);
        }
        return paymentDTOS;
    }

    public boolean update(Payment paymentDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE payment SET amount=?, date=? WHERE payment_id=?", paymentDTO.getAmount(), paymentDTO.getDate(), paymentDTO.getPayment_id());
    }

    public boolean delete(String payment_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment WHERE payment_id=?", payment_id);
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT payment_id FROM payment");

        ArrayList<String> payment_ids = new ArrayList<>();

        while (rst.next()) {
            payment_ids.add(rst.getString("payment_id"));
        }
        return payment_ids;
    }

    public Payment findById(String selectedPayId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment WHERE payment_id = ?", selectedPayId);
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString("payment_id"),  // payment ID
                    rst.getDouble("amount"),
                    rst.getDate("date").toLocalDate()
            );
            payments.add(payment);
        }
        return null;
    }

}
