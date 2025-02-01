package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.OrdersBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.OrdersDAO;
import com.example.stitchwave.entity.Orders;

import java.sql.SQLException;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERS);

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        String nextId = ordersDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("R%03d", id);
        } else {
            return "R001";
        }
    }

    @Override
    public Orders findByOrderId(String selectedId) throws SQLException, ClassNotFoundException {
        return ordersDAO.findById(selectedId);
    }
}
