package com.example.stitchwave.view.tdm;

import java.time.LocalDate;

public class SupplierOrderTM implements Comparable<SupplierOrderTM>{
    private String order_id;
    private double qty_kg;
    private LocalDate date;
    private String supplier_id;

    public SupplierOrderTM(String order_id, double qty_kg, LocalDate date, String supplier_id) {
        this.order_id = order_id;
        this.qty_kg = qty_kg;
        this.date = date;
        this.supplier_id = supplier_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public double getQty_kg() {
        return qty_kg;
    }

    public void setQty_kg(double qty_kg) {
        this.qty_kg = qty_kg;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    @Override
    public String toString(){
        return "SupplierOrderTM{" +
                "order_id='" + order_id + '\'' +
                ", qty_kg='" + qty_kg + '\'' +
                ", date=" + date +
                ", supplier_id='" + supplier_id + '\'' +
                '}';
    }

    @Override
    public int compareTo(SupplierOrderTM o) {
        return order_id.compareTo(o.getOrder_id());
    }
}
