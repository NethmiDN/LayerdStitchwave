package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.FabricDAO;
import com.example.stitchwave.entity.Fabric;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FabricDAOImpl implements FabricDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT fabric_id FROM fabric ORDER BY fabric_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Fabric> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT fabric_id FROM fabric");

        ArrayList<String> fabric_ids = new ArrayList<>();

        while (rst.next()) {
            fabric_ids.add(rst.getString("fabric_id"));
        }
        return fabric_ids;
    }

    @Override
    public Fabric findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM fabric WHERE fabric_id = ?", id);
        ArrayList<Fabric> fabrics = new ArrayList<>();
        while (rst.next()) {
            Fabric fabric = new Fabric(
                    rst.getString("fabric_id"),  // fabric ID
                    rst.getString("color"),
                    rst.getDouble("weight_kg"),
                    rst.getDouble("width_inch")
            );
            fabrics.add(fabric);
        }
        return null;
    }

    @Override
    public boolean save(Fabric DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Fabric DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean saveFabric(Fabric fabric) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO fabric (fabric_id, color, weight_kg, width_inch) VALUES (?, ?, ?,?)",
                fabric.getFabric_id(),
                fabric.getColor(),
                fabric.getWeight_kg(),
                fabric.getWidth_inch()
        );
    }
}