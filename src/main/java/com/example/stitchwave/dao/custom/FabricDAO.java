package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Fabric;

import java.sql.SQLException;

public interface FabricDAO extends CrudDAO<Fabric> {
    boolean saveFabric(Fabric fabric) throws SQLException, ClassNotFoundException;

}