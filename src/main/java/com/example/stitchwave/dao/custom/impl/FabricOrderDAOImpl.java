package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.FabricOrderDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FabricOrderDAOImpl implements FabricOrderDAO {
    @Override
    public boolean saveFabricOrder(String fabricId, String orderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO fabric_order (fabric_id, order_id) VALUES (?, ?)",
                fabricId,
                orderId
        );
    }

    @Override
    public boolean save(Object DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public List<Object> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Object findById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
