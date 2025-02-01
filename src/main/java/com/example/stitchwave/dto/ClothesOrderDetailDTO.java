package com.example.stitchwave.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ClothesOrderDetailDTO implements Serializable {
    private String order_id;
    private String stock_id;
    private LocalDate date;
    private int qty;
    private String customer_id;
    private String payment_id;

    public ClothesOrderDetailDTO() {
    }

    public ClothesOrderDetailDTO(String order_id, String stock_id, LocalDate date, int qty, String customer_id, String payment_id) {
        this.order_id = order_id;
        this.stock_id = stock_id;
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

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
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
        return "ClothesOrderDetailTM{" +
                "order_id='" + order_id + '\'' +
                ", stock_id='" + stock_id + '\'' +
                ", date=" + date +
                ", qty=" + qty +
                ", customer_id='" + customer_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                '}';
    }
}
