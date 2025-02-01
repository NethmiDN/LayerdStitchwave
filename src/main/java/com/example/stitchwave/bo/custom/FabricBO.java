package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.entity.Fabric;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FabricBO extends SuperBO {
    public String getNextFabricId() throws SQLException, ClassNotFoundException;

    public ArrayList<String> getAllFabricIds() throws SQLException, ClassNotFoundException;

    public Fabric findByFabricId(String selectedFabId) throws SQLException, ClassNotFoundException;
}
