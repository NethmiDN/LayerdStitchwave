package com.example.stitchwave.entity;

import java.io.Serializable;

public class Fabric implements Serializable {
    private String fabric_id;
    private String color;
    private Double weight_kg;
    private Double width_inch;

    public Fabric(String fabric_id, String color, Double weight_kg, Double width_inch) {
        this.fabric_id = fabric_id;
        this.color = color;
        this.weight_kg = weight_kg;
        this.width_inch = width_inch;
    }

    public String getFabric_id() {
        return fabric_id;
    }

    public void setFabric_id(String fabric_id) {
        this.fabric_id = fabric_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(Double weight_kg) {
        this.weight_kg = weight_kg;
    }

    public Double getWidth_inch() {
        return width_inch;
    }

    public void setWidth_inch(Double width_inch) {
        this.width_inch = width_inch;
    }

    @Override
    public String toString() {
        return "FabricTM{" +
                "fabric_id='" + fabric_id + '\'' +
                ", color='" + color + '\'' +
                ", weight_kg='" + weight_kg + '\'' +
                ", width_inch='" + width_inch + '\'' +
                '}';
    }
}
