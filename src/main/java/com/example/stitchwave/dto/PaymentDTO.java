package com.example.stitchwave.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PaymentDTO implements Serializable {
    private String payment_id;
    private Double amount;
    private LocalDate date;

    public PaymentDTO() {
    }

    public PaymentDTO(String payment_id, Double amount, LocalDate date) {
        this.payment_id = payment_id;
        this.amount = amount;
        this.date = date;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount
    ) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "payment_id='" + payment_id + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
