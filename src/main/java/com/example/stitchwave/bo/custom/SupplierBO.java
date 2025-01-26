package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.EmployeeDTO;
import com.example.stitchwave.dto.SupplierDTO;
import com.example.stitchwave.entity.Employee;
import com.example.stitchwave.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDTO DTO) throws SQLException, ClassNotFoundException;

    boolean updateSupplier(SupplierDTO DTO) throws SQLException, ClassNotFoundException;

    String getNextSupplierId() throws SQLException, ClassNotFoundException;

    ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;

    ArrayList<Object> getAllSupplierIds() throws SQLException, ClassNotFoundException;

    Supplier findBySupplierId(String selectedId) throws SQLException, ClassNotFoundException;

    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
}
