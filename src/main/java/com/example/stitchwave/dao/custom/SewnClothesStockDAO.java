package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.SewnClothesStock;

import java.sql.SQLException;
import java.util.Map;

public interface SewnClothesStockDAO extends CrudDAO<SewnClothesStock> {
    public Map<String, Integer> getLowStockItems(int threshold) throws SQLException, ClassNotFoundException ;

    public boolean delete(String stock_id) throws SQLException, ClassNotFoundException ;

    }
