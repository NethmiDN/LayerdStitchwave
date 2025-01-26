package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.EmployeeDAO;
import com.example.stitchwave.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString("employee_id");
        }
        return null;
    }

    public boolean save(Employee employeeDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VALUES (?,?,?)", employeeDTO.getEmployee_id(), employeeDTO.getName(), employeeDTO.getContact());
    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");

        ArrayList<Employee> employeeDTOs = new ArrayList<>();

        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
            employeeDTOs.add(employee);
        }
        return employeeDTOs;
    }

    public boolean update(Employee employeeDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET name=?, contact=? WHERE employee_id=?", employeeDTO.getName(), employeeDTO.getContact(), employeeDTO.getEmployee_id());
    }

    public boolean delete(String employee_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id=?", employee_id);
    }

    public ArrayList<Employee> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee");

        ArrayList<Employee> employee_ids = new ArrayList<>();

        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString("employee_id"),  // customer ID
                    rst.getString("name"),  // Name
                    rst.getString("contact")  // Contact
            );
            employee_ids.add(employee);
        }
        return employee_ids;
    }

    public Employee findById(String selectedEmpId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE employee_id=?", selectedEmpId);

        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString("employee_id"),  // customer ID
                    rst.getString("name"),  // Name
                    rst.getString("contact")  // Contact
            );
            employees.add(employee);
        }
        return employees.get(0);
    }
}
