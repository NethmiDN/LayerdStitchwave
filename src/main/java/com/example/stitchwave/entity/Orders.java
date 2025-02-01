package com.example.stitchwave.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Orders implements Serializable {
    private String order_id;
    private LocalDate date;
    private Integer qty;
    private String customer_id;
    public String payment_id;

    public Orders() {
    }

    public Orders(String order_id, LocalDate date, Integer qty, String customer_id, String payment_id) {
        this.order_id = order_id;
        this.date = date;
        this.qty = qty;
        this.customer_id = customer_id;
        this.payment_id = payment_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    @Override
    public String toString() {
        return "OrdersTM{" +
                "order_id='" + order_id + '\'' +
                ", date=" + date +
                ", qty=" + qty +
                ", customer_id='" + customer_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                '}';
    }
}