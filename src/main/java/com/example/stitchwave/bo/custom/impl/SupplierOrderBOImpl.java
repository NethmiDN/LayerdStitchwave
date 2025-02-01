package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.SupplierOrderBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.FabricDAO;
import com.example.stitchwave.dao.custom.FabricOrderDAO;
import com.example.stitchwave.dao.custom.SupplierOrderDAO;
import com.example.stitchwave.dto.FabricDTO;
import com.example.stitchwave.dto.SupplierOrderDTO;
import com.example.stitchwave.entity.Fabric;
import com.example.stitchwave.entity.SupplierOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderBOImpl implements SupplierOrderBO {
    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER_ORDER);
    FabricDAO fabricDAO = (FabricDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FABRIC);
    FabricOrderDAO fabricOrderDAO = (FabricOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FABRIC_ORDER);

    @Override
    public String getNextSupplierOrderId() throws SQLException, ClassNotFoundException {
        String nextId = supplierOrderDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("D%03d", id);
        } else {
            return "D001";
        }
    }

    @Override
    public boolean saveOrderWithFabrics(SupplierOrderDTO orderDTO, ArrayList<FabricDTO> fabricDTOs) throws SQLException, ClassNotFoundException {
        boolean transactionSuccess = false;

        try {
            SQLUtil.setAutoCommit(false);

            SupplierOrder order = new SupplierOrder(orderDTO.getOrder_id(), orderDTO.getQty_kg(), orderDTO.getDate(), orderDTO.getSupplier_id());
            boolean orderSaved = supplierOrderDAO.saveOrderWithFabrics(order, null);

            boolean fabricSaved = true;

            for (FabricDTO fabricDTO : fabricDTOs) {
                Fabric fabric = new Fabric(fabricDTO.getFabric_id(), fabricDTO.getColor(), fabricDTO.getWeight_kg(), fabricDTO.getWidth_inch());
                fabricSaved = fabricDAO.saveFabric(fabric);

                if (fabricSaved) {
                    fabricSaved = fabricOrderDAO.saveFabricOrder(fabric.getFabric_id(), orderDTO.getOrder_id());
                }

                if (!fabricSaved) break;
            }

            if (orderSaved && fabricSaved) {
                SQLUtil.commit();
                transactionSuccess = true;
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

    @Override
    public boolean updateSupplierOrder(SupplierOrderDTO supplierOrderDTO) {
        return false;
    }

    @Override
    public boolean deleteSupplierOrder(String orderId) {
        return false;
    }

    @Override
    public List<SupplierOrder> getAllSupplierOrders() throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.getAll();
    }
}