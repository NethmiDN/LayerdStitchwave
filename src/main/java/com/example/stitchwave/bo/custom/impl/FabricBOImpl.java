package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.FabricBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.FabricDAO;
import com.example.stitchwave.entity.Fabric;

import java.sql.SQLException;
import java.util.ArrayList;

public class FabricBOImpl implements FabricBO {
    FabricDAO fabricDAO = (FabricDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FABRIC);

    @Override
    public String getNextFabricId() throws SQLException, ClassNotFoundException {
        String nextId = fabricDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("F%03d", id);
        } else {
            return "F001";
        }
    }

    @Override
    public ArrayList<String> getAllFabricIds() throws SQLException, ClassNotFoundException {
        return fabricDAO.getAllIds();
    }

    @Override
    public Fabric findByFabricId(String selectedId) throws SQLException, ClassNotFoundException {
        return fabricDAO.findById(selectedId);
    }

}
