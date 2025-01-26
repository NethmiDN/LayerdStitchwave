package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.SupplierBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.SupplierDAO;
import com.example.stitchwave.dto.SupplierDTO;
import com.example.stitchwave.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public boolean saveSupplier(SupplierDTO DTO) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(DTO.getSupplier_id(), DTO.getName(), DTO.getContact());
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean updateSupplier(SupplierDTO DTO) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(DTO.getSupplier_id(), DTO.getName(), DTO.getContact());
            return supplierDAO.update(supplier);
    }

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        String nextId = supplierDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("S%03d", id);
        } else {
            return "S001";
        }
    }

    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = (ArrayList<Supplier>) supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplier_id(supplier.getSupplier_id());
            supplierDTO.setName(supplier.getName());
            supplierDTO.setContact(supplier.getContact());
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    @Override
    public ArrayList<Object> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierDAO.getAllIds();
        ArrayList<Object> supplierIds = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierIds.add(supplier.getSupplier_id());
        }
        return supplierIds;
    }

    @Override
    public Supplier findBySupplierId(String selectedId) throws SQLException, ClassNotFoundException {
        return supplierDAO.findById(selectedId);
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        supplierDAO.delete(id);
        return false;
    }

}
