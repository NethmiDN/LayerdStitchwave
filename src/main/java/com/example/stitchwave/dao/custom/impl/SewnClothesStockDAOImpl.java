package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.SewnClothesStockDAO;
import com.example.stitchwave.entity.SewnClothesStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SewnClothesStockDAOImpl implements SewnClothesStockDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT stock_id FROM sewn_clothes_stock ORDER BY stock_id DESC LIMIT 1");

        if (rst.next()) {
            return rst.getString("stock_id");
        }
        return null;
    }

    public boolean save(SewnClothesStock sewnClothesStockDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO sewn_clothes_stock VALUES (?,?,?)", sewnClothesStockDTO.getStock_id(), sewnClothesStockDTO.getQty(), sewnClothesStockDTO.getFabric_id());
    }

    public ArrayList<SewnClothesStock> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM sewn_clothes_stock");

        ArrayList<SewnClothesStock> sewnClothesStockDTOS = new ArrayList<>();

        while (rst.next()) {
            SewnClothesStock sewnClothesStockDTO = new SewnClothesStock(
                    rst.getString("stock_id"),  // stock ID
                    rst.getInt("qty"),  // qty
                    rst.getString("fabric_id") //fabric id
            );
            sewnClothesStockDTOS.add(sewnClothesStockDTO);
        }
        return sewnClothesStockDTOS;
    }

    public boolean update(SewnClothesStock sewnClothesStockDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE sewn_clothes_stock SET qty=?, fabric_id=? WHERE stock_id=?",
                sewnClothesStockDTO.getQty(),
                sewnClothesStockDTO.getFabric_id(),
                sewnClothesStockDTO.getStock_id()
        );
    }

    public boolean delete(String stock_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM sewn_clothes_stock WHERE stock_id=?", stock_id);
    }

    public Map<String, Integer> getLowStockItems(int threshold) throws SQLException, ClassNotFoundException {
        Map<String, Integer> lowStockItems = new HashMap<>();

        ResultSet rst = SQLUtil.execute(
                "SELECT stock_id, qty FROM sewn_clothes_stock WHERE qty < ?",
                threshold
        );

        while (rst.next()) {
            String stockId = rst.getString("stock_id");
            int quantity = rst.getInt("qty");
            lowStockItems.put(stockId, quantity);
        }

        return lowStockItems;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT stock_id FROM sewn_clothes_stock");

        ArrayList<String> stock_ids = new ArrayList<>();

        while (rst.next()) {
            stock_ids.add(rst.getString("stock_id"));
        }
        return stock_ids;
    }

    public SewnClothesStock findById(String selectedStockId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM sewn_clothes_stock WHERE stock_id=?", selectedStockId);
        if (rst.next()) {
            return new SewnClothesStock(
                    rst.getString("stock_id"),
                    rst.getInt("qty"),
                    rst.getString("fabric_id")
            );
        }
        return null;
    }
}