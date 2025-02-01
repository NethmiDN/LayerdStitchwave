package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.entity.Orders;

import java.sql.SQLException;

public interface OrdersBO extends SuperBO {
    String getNextOrderId() throws SQLException, ClassNotFoundException;

    Orders findByOrderId(String selectedId) throws SQLException, ClassNotFoundException;
}
