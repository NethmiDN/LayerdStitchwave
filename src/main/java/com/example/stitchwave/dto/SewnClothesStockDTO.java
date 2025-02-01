package com.example.stitchwave.dto;

import java.io.Serializable;

public class SewnClothesStockDTO implements Serializable {
    private String stock_id;
    private Integer qty;
    private String fabric_id;

    public SewnClothesStockDTO() {
    }

    public SewnClothesStockDTO(String stock_id, Integer qty, String fabric_id) {
        this.stock_id = stock_id;
        this.qty = qty;
        this.fabric_id = fabric_id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getFabric_id() {
        return fabric_id;
    }

    public void setFabric_id(String fabric_id) {
        this.fabric_id = fabric_id;
    }

    @Override
    public String toString() {
        return "SewnClothesStockTM{" +
                "stock_id='" + stock_id + '\'' +
                ", qty=" + qty +
                ", fabric_id='" + fabric_id + '\'' +
                '}';
    }
}
