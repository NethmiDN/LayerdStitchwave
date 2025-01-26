package com.example.stitchwave.view.tdm;

public class CustomerTM implements Comparable<CustomerTM> {
    private String customer_id;
    private String name;
    private String contact;

    public CustomerTM(String customer_id, String name, String contact) {
        this.customer_id = customer_id;
        this.name = name;
        this.contact = contact;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
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
    public int compareTo(CustomerTM o) {
        return customer_id.compareTo(o.getCustomer_id());
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
