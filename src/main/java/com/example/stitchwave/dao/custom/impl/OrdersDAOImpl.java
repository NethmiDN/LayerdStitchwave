package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.OrdersDAO;
import com.example.stitchwave.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public boolean save(Orders DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

        if (rst.next()) {
            return rst.getString("order_id");
        }
        return null;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    public Orders findById(String selectedOrderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE order_id=?", selectedOrderId);

        ArrayList<Orders> orders = new ArrayList<>();
        while (rst.next()) {
            Orders order = new Orders(
                    rst.getString("order_id"),  // Order ID
                    rst.getDate("date").toLocalDate() , // Date
                    rst.getInt("qty"),  // qty
                    rst.getString("customer_id"), // customer id
                    rst.getString("payment_id") // payment id
            );
            orders.add(order);
        }
        return orders.get(0);
    }
}
