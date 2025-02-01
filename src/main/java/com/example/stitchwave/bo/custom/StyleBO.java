package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.StyleDTO;
import com.example.stitchwave.entity.Style;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StyleBO extends SuperBO {
    boolean saveStyle(StyleDTO DTO) throws SQLException, ClassNotFoundException;

    String getNextStyleId() throws SQLException, ClassNotFoundException;

    ArrayList<StyleDTO> getAllStyle() throws SQLException, ClassNotFoundException;

    boolean updateStyle(StyleDTO DTO) throws SQLException, ClassNotFoundException;

    Style findByStyleId(String selectedId) throws SQLException, ClassNotFoundException;

    boolean deleteStyle(String id) throws SQLException, ClassNotFoundException;
}
