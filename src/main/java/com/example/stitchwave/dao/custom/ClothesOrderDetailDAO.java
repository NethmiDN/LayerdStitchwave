package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.ClothesOrderDetail;
import com.example.stitchwave.view.tdm.ClothesOrderDetailTM;

import java.sql.SQLException;

public interface ClothesOrderDetailDAO extends CrudDAO<ClothesOrderDetail> {
    boolean saveOrder(ClothesOrderDetailTM order) throws SQLException, ClassNotFoundException;
    boolean saveClothesOrder(String stockId, String orderId) throws SQLException, ClassNotFoundException;
    boolean updateStock(String stockId, int orderQty) throws SQLException, ClassNotFoundException;
    int getStockQty(String stockId) throws SQLException, ClassNotFoundException;    }
