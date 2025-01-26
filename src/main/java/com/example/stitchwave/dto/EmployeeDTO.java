package com.example.stitchwave.dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private String employee_id;
    private String name;
    private String contact;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String employee_id, String name, String contact) {
        this.employee_id = employee_id;
        this.name = name;
        this.contact = contact;
    }

    public String getEmployee_id (){
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
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
        return "EmployeeTM{" +
                "employee_id='" + employee_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
