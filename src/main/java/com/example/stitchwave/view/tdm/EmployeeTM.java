package com.example.stitchwave.view.tdm;

public class EmployeeTM implements Comparable<EmployeeTM>{
    private String employee_id;
    private String name;
    private String contact;

    public EmployeeTM(String employee_id, String name, String contact) {
        this.employee_id = employee_id;
        this.name = name;
        this.contact = contact;
    }

    public String getEmployee_id() {
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
    public int compareTo(EmployeeTM o) {
        return employee_id.compareTo(o.getEmployee_id());
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
