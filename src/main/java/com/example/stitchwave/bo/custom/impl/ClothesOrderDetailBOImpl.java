package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.ClothesOrderDetailBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.ClothesOrderDetailDAO;
import com.example.stitchwave.dto.ClothesOrderDetailDTO;
import com.example.stitchwave.entity.ClothesOrderDetail;
import com.example.stitchwave.view.tdm.ClothesOrderDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClothesOrderDetailBOImpl implements ClothesOrderDetailBO {
    ClothesOrderDetailDAO clothesOrderDetailDAO = (ClothesOrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLOTHES_ORDER_DETAIL);

    @Override
    public ArrayList<ClothesOrderDetailDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<ClothesOrderDetail> clothesOrderDetails = (ArrayList<ClothesOrderDetail>) clothesOrderDetailDAO.getAll();
        ArrayList<ClothesOrderDetailDTO> clothesOrderDetailDTOS = new ArrayList<>();
        for (ClothesOrderDetail clothesOrderDetail : clothesOrderDetails) {
            ClothesOrderDetailDTO clothesOrderDetailsDTO = new ClothesOrderDetailDTO();
            clothesOrderDetailsDTO.setOrder_id(clothesOrderDetail.getOrder_id());
            clothesOrderDetailsDTO.setStock_id(clothesOrderDetail.getStock_id());
            clothesOrderDetailsDTO.setDate(clothesOrderDetail.getDate());
            clothesOrderDetailsDTO.setQty(clothesOrderDetail.getQty());
            clothesOrderDetailsDTO.setCustomer_id(clothesOrderDetail.getCustomer_id());
            clothesOrderDetailsDTO.setPayment_id(clothesOrderDetail.getPayment_id());
            clothesOrderDetailDTOS.add(clothesOrderDetailsDTO);
        }
        return clothesOrderDetailDTOS;
    }

    @Override
    public boolean saveOrderWithStockReduction(ClothesOrderDetailTM clothesOrderDetails, int orderQty) throws SQLException, ClassNotFoundException {
        boolean transactionSuccess = false;

        try {
            SQLUtil.setAutoCommit(false);

            int currentStockQty = clothesOrderDetailDAO.getStockQty(clothesOrderDetails.getStock_id());

            if (currentStockQty >= orderQty) {
                boolean orderSaved = clothesOrderDetailDAO.saveOrder(clothesOrderDetails);
                boolean clothesOrderSaved = clothesOrderDetailDAO.saveClothesOrder(clothesOrderDetails.getStock_id(), clothesOrderDetails.getOrder_id());
                boolean stockUpdated = clothesOrderDetailDAO.updateStock(clothesOrderDetails.getStock_id(), orderQty);

                if (orderSaved && clothesOrderSaved && stockUpdated) {
                    SQLUtil.commit();
                    transactionSuccess = true;
                } else {
                    SQLUtil.rollback();
                }
            } else {
                SQLUtil.rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            SQLUtil.rollback();
            e.printStackTrace();
        } finally {
            SQLUtil.setAutoCommit(true);
        }

        return transactionSuccess;
    }
}