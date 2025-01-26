package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {
    public boolean delete(String selectedId) throws SQLException, ClassNotFoundException ;

}
