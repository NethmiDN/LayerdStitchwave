package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.SewnClothesStockBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.SewnClothesStockDAO;
import com.example.stitchwave.dto.SewnClothesStockDTO;
import com.example.stitchwave.entity.SewnClothesStock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class SewnClothesStockBOImpl implements SewnClothesStockBO {
    SewnClothesStockDAO sewnClothesStockDAO = (SewnClothesStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SEWN_CLOTHES_STOCK);

    @Override
    public boolean saveSewnClothesStock(SewnClothesStockDTO DTO) throws SQLException, ClassNotFoundException {
        SewnClothesStock sewnClothesStock = new SewnClothesStock(DTO.getStock_id(), DTO.getQty(), DTO.getFabric_id());
        return sewnClothesStockDAO.save(sewnClothesStock);
    }

    @Override
    public boolean updateSewnClothesStock(SewnClothesStockDTO DTO) throws SQLException, ClassNotFoundException {
        SewnClothesStock sewnClothesStock = new SewnClothesStock(DTO.getStock_id(), DTO.getQty(), DTO.getFabric_id());
        return sewnClothesStockDAO.update(sewnClothesStock);
    }

    @Override
    public String getNextSewnClothesStockId() throws SQLException, ClassNotFoundException {
        String nextId = sewnClothesStockDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("W%03d", id);
        } else {
            return "W001";
        }
    }

    @Override
    public ArrayList<SewnClothesStockDTO> getAllSewnClothesStock() throws SQLException, ClassNotFoundException {
        ArrayList<SewnClothesStock> sewnClothesStocks = (ArrayList<SewnClothesStock>) sewnClothesStockDAO.getAll();
        ArrayList<SewnClothesStockDTO> sewnClothesStockDTOS = new ArrayList<>();
        for (SewnClothesStock sewnClothesStock : sewnClothesStocks) {
            SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO();
            sewnClothesStockDTO.setStock_id(sewnClothesStock.getStock_id());
            sewnClothesStockDTO.setQty(sewnClothesStock.getQty());
            sewnClothesStockDTO.setFabric_id(sewnClothesStock.getFabric_id());
            sewnClothesStockDTOS.add(sewnClothesStockDTO);
        }
        return sewnClothesStockDTOS;
    }

    @Override
    public ArrayList<String> getAllStockIds() throws SQLException, ClassNotFoundException {
        return sewnClothesStockDAO.getAllIds();
    }

    @Override
    public SewnClothesStock findByStockId(String selectedId) throws SQLException, ClassNotFoundException {
        return sewnClothesStockDAO.findById(selectedId);
    }

    @Override
    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException {
        sewnClothesStockDAO.delete(id);
        return false;
    }

    @Override
    public Map<String, Integer> getLowStockItems(int threshold) throws SQLException, ClassNotFoundException {
        return sewnClothesStockDAO.getLowStockItems(threshold);
    }
}
