package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.SewnClothesStockDTO;
import com.example.stitchwave.entity.SewnClothesStock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface SewnClothesStockBO extends SuperBO {
    boolean saveSewnClothesStock(SewnClothesStockDTO DTO) throws SQLException, ClassNotFoundException;

    boolean updateSewnClothesStock(SewnClothesStockDTO DTO) throws SQLException, ClassNotFoundException;

    String getNextSewnClothesStockId() throws SQLException, ClassNotFoundException;

    ArrayList<SewnClothesStockDTO> getAllSewnClothesStock() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllStockIds() throws SQLException, ClassNotFoundException;

    SewnClothesStock findByStockId(String selectedId) throws SQLException, ClassNotFoundException;

    boolean deleteStock(String id) throws SQLException, ClassNotFoundException;

    Map<String, Integer> getLowStockItems(int threshold) throws SQLException, ClassNotFoundException;
}
