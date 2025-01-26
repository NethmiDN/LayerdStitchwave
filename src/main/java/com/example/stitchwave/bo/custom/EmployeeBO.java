package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.EmployeeDTO;
import com.example.stitchwave.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDTO DTO) throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDTO DTO) throws SQLException, ClassNotFoundException;

    String getNextEmployeeId() throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

    ArrayList<Object> getAllEmployeeIds() throws SQLException, ClassNotFoundException;

    Employee findByEmployeeId(String selectedId) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
}
