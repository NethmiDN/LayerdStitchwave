package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;

import java.sql.SQLException;

public interface FabricOrderDAO extends CrudDAO<Object> {
    boolean saveFabricOrder(String fabricId, String orderId) throws SQLException, ClassNotFoundException;
}
