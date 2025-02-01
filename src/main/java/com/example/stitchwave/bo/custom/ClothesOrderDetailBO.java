package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.ClothesOrderDetailDTO;
import com.example.stitchwave.view.tdm.ClothesOrderDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClothesOrderDetailBO extends SuperBO {
    ArrayList<ClothesOrderDetailDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    boolean saveOrderWithStockReduction(ClothesOrderDetailTM clothesOrderDetails, int orderQty) throws SQLException, ClassNotFoundException;
}
