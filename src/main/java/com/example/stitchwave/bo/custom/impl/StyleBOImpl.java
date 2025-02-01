package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.StyleBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.StyleDAO;
import com.example.stitchwave.dto.StyleDTO;
import com.example.stitchwave.entity.Style;

import java.sql.SQLException;
import java.util.ArrayList;

public class StyleBOImpl implements StyleBO {
    StyleDAO styleDAO = (StyleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STYLE);

    @Override
    public boolean saveStyle(StyleDTO DTO) throws SQLException, ClassNotFoundException {
        Style style = new Style(DTO.getStyle_id(), DTO.getSize(), DTO.getQty(),DTO.getEmployee_id(),DTO.getStock_id());
        return styleDAO.save(style);
    }

    @Override
    public String getNextStyleId() throws SQLException, ClassNotFoundException {
        String nextId = styleDAO.getNextId();
        if (nextId != null) {
            int id = Integer.parseInt(nextId.substring(1)) + 1;
            return String.format("L%03d", id);
        } else {
            return "L001";
        }
    }

    @Override
    public ArrayList<StyleDTO> getAllStyle() throws SQLException, ClassNotFoundException {
        ArrayList<Style> styles = (ArrayList<Style>) styleDAO.getAll();
        ArrayList<StyleDTO> styleDTOS = new ArrayList<>();
        for (Style style : styles) {
            StyleDTO styleDTO = new StyleDTO();
            styleDTO.setStyle_id(style.getStyle_id());
            styleDTO.setSize(style.getSize());
            styleDTO.setQty(style.getQty());
            styleDTO.setEmployee_id(style.getEmployee_id());
            styleDTO.setStock_id(style.getStock_id());
            styleDTOS.add(styleDTO);
        }
        return styleDTOS;
    }

    @Override
    public boolean updateStyle(StyleDTO DTO) throws SQLException, ClassNotFoundException {
        Style style = new Style(DTO.getStyle_id(), DTO.getSize(), DTO.getQty(),DTO.getEmployee_id(),DTO.getStock_id());
        return styleDAO.update(style);
    }

    @Override
    public Style findByStyleId(String selectedId) throws SQLException, ClassNotFoundException {
        return styleDAO.findById(selectedId);
    }

    @Override
    public boolean deleteStyle(String id) throws SQLException, ClassNotFoundException {
        styleDAO.delete(id);
        return false;
    }

}
