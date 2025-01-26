package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.SupplierDAO;
import com.example.stitchwave.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString("supplier_id");
        }
        return null;
    }

    public boolean save(Supplier supplierDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier VALUES (?,?,?)", supplierDTO.getSupplier_id(), supplierDTO.getName(), supplierDTO.getContact());
    }

    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");

        ArrayList<Supplier> supplierDTOs = new ArrayList<>();

        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
            supplierDTOs.add(supplier);
        }
        return supplierDTOs;
    }

    public boolean update(Supplier supplierDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET name=?, contact=? WHERE supplier_id=?", supplierDTO.getName(), supplierDTO.getContact(), supplierDTO.getSupplier_id());
    }

    public boolean delete(String supplier_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supplier_id=?", supplier_id);
    }

    public ArrayList<Supplier> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM supplier");

        ArrayList<Supplier> supplier_ids = new ArrayList<>();

        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString("supplier_id"),  // customer ID
                    rst.getString("name"),  // Name
                    rst.getString("contact")  // Contact
            );
            supplier_ids.add(supplier);
        }
        return supplier_ids;
    }

    public Supplier findById(String selectedSupId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE supplier_id=?", selectedSupId);

        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString("supplier_id"),  // customer ID
                    rst.getString("name"),  // Name
                    rst.getString("contact")  // Contact
            );
            suppliers.add(supplier);
        }
        return suppliers.get(0);
    }
}
