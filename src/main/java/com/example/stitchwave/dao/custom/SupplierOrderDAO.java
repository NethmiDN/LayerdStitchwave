package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.SupplierOrder;
import com.example.stitchwave.entity.Fabric;

import java.sql.SQLException;
import java.util.List;

public interface SupplierOrderDAO extends CrudDAO<SupplierOrder> {
    String getNextId() throws SQLException, ClassNotFoundException;

    boolean saveOrderWithFabrics(SupplierOrder order, List<Fabric> fabrics) throws SQLException ,ClassNotFoundException;

}