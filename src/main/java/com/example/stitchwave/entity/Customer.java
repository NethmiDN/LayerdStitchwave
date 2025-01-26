package com.example.stitchwave.entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customer_id;
    private String name;
    private String contact;

    public Customer() {
    }

    public Customer(String customer_id, String name, String contact) {
        this.customer_id = customer_id;
        this.name = name;
        this.contact = contact;
    }

    public String getCustomer_id (){
        return customer_id;
    }

    public void setCustomerId(String customer_id) {
        this.customer_id = customer_id;
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
        return "CustomerTM{" +
                "customer_id='" + customer_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
