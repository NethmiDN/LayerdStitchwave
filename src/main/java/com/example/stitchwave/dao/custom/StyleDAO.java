package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.Style;

import java.sql.SQLException;

public interface StyleDAO extends CrudDAO<Style> {
    public boolean delete(String style_id) throws SQLException, ClassNotFoundException ;
    }
