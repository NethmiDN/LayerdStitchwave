package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.SupplierOrderDAO;
import com.example.stitchwave.entity.SupplierOrder;
import com.example.stitchwave.entity.Fabric;
import com.example.stitchwave.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderDAOImpl implements SupplierOrderDAO {
    @Override
    public boolean save(SupplierOrder DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(SupplierOrder DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM supplier_order ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<SupplierOrder> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier_order");

        ArrayList<SupplierOrder> supplierOrders = new ArrayList<>();

        while (rst.next()) {
            SupplierOrder supplierOrder = new SupplierOrder(
                    rst.getString("order_id"),
                    rst.getDouble("qty_kg"),
                    rst.getDate("date").toLocalDate(),
                    rst.getString("supplier_id")
            );
            supplierOrders.add(supplierOrder);
        }
        return supplierOrders;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM supplier_order");

        ArrayList<String> order_ids = new ArrayList<>();

        while (rst.next()) {
            order_ids.add(rst.getString("order_id"));
        }
        return order_ids;
    }

    @Override
    public SupplierOrder findById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveOrderWithFabrics(SupplierOrder order, List<Fabric> fabrics) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier_order (order_id, qty_kg, date, supplier_id) VALUES (?, ?, ?, ?)", order.getOrder_id(), order.getQty_kg(), order.getDate(), order.getSupplier_id()
        );
    }
}