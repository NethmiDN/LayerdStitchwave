package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public boolean delete(String selectedId) throws SQLException, ClassNotFoundException ;

}
