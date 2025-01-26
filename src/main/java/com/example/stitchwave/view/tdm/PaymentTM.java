package com.example.stitchwave.view.tdm;

import java.time.LocalDate;

public class PaymentTM implements Comparable<PaymentTM>{
    private String payment_id;
    private Double amount;
    private LocalDate date;

    public PaymentTM(String payment_id, Double amount, LocalDate date) {
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
    public int compareTo(PaymentTM o) {
        return payment_id.compareTo(o.getPayment_id());
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
