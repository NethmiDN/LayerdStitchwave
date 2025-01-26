package com.example.stitchwave.dto;

import java.io.Serializable;

public class SupplierDTO implements Serializable {
    private String supplier_id;
    private String name;
    private String contact;

    public SupplierDTO() {
    }

    public SupplierDTO(String supplier_id, String name, String contact) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact = contact;
    }

    public String getSupplier_id (){
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "supplier_id='" + supplier_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
