package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.FabricDTO;
import com.example.stitchwave.dto.SupplierOrderDTO;
import com.example.stitchwave.entity.SupplierOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierOrderBO extends SuperBO {
    String getNextSupplierOrderId() throws SQLException, ClassNotFoundException;

    boolean saveOrderWithFabrics(SupplierOrderDTO orderDTO, ArrayList<FabricDTO> fabricDTO) throws SQLException, ClassNotFoundException;

    boolean updateSupplierOrder(SupplierOrderDTO supplierOrderDTO);

    boolean deleteSupplierOrder(String orderId);

    List<SupplierOrder> getAllSupplierOrders() throws SQLException, ClassNotFoundException;
}