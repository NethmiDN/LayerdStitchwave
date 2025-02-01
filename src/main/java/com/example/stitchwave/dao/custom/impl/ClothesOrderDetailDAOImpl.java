package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.ClothesOrderDetailDAO;
import com.example.stitchwave.entity.ClothesOrderDetail;
import com.example.stitchwave.view.tdm.ClothesOrderDetailTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClothesOrderDetailDAOImpl implements ClothesOrderDetailDAO {
    @Override
    public boolean save(ClothesOrderDetail DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ClothesOrderDetail DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString("order_id");
        }
        return null;
    }

    public ArrayList<ClothesOrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = null;
        try {
            rst = SQLUtil.execute("""
            SELECT o.order_id, o.date, co.stock_id, o.qty,
                   c.customer_id, p.payment_id
            FROM orders o
            JOIN clothes_order co ON o.order_id = co.order_id
            JOIN customer c ON o.customer_id = c.customer_id
            JOIN payment p ON o.payment_id = p.payment_id;
            """);

            ArrayList<ClothesOrderDetail> clothesOrderDetailDTOS = new ArrayList<>();

            while (rst.next()) {
                ClothesOrderDetail clothesOrderDetails = new ClothesOrderDetail(
                        rst.getString("order_id"),
                        rst.getString("stock_id"),
                        rst.getDate("date").toLocalDate(),
                        rst.getInt("qty"),
                        rst.getString("customer_id"),
                        rst.getString("payment_id")
                );
                clothesOrderDetailDTOS.add(clothesOrderDetails);
            }
            return clothesOrderDetailDTOS;
        } finally {
            if (rst != null) {
                rst.close();
            }
        }
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT stock_id FROM clothes_order");

        ArrayList<String> stock_ids = new ArrayList<>();

        while (rst.next()) {
            stock_ids.add(rst.getString("stock_id"));
        }
        return stock_ids;
    }

    @Override
    public ClothesOrderDetail findById(String selectedId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveOrder(ClothesOrderDetailTM order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO orders (order_id, date, qty, customer_id, payment_id) VALUES (?, ?, ?, ?, ?)",
                order.getOrder_id(),
                order.getDate(),
                order.getQty(),
                order.getCustomer_id(),
                order.getPayment_id()
        );
    }

    @Override
    public boolean saveClothesOrder(String stockId, String orderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO clothes_order (stock_id, order_id) VALUES (?, ?)", stockId, orderId);
    }

    @Override
    public boolean updateStock(String stockId, int orderQty) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE sewn_clothes_stock SET qty = qty - ? WHERE stock_id = ?", orderQty, stockId);
    }

    @Override
    public int getStockQty(String stockId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT qty FROM sewn_clothes_stock WHERE stock_id = ?", stockId);
        return rst.next() ? rst.getInt("qty") : -1;
    }
}