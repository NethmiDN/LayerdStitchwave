package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.StyleDAO;
import com.example.stitchwave.entity.Style;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StyleDAOImpl implements StyleDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT style_id FROM style ORDER BY style_id DESC LIMIT 1");

        if (rst.next()) {
            return rst.getString("style_id");
        }
        return null;
    }

    public boolean save(Style styleDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO style VALUES (?,?,?,?,?)", styleDTO.getStyle_id(), styleDTO.getSize(), styleDTO.getQty(), styleDTO.getEmployee_id(), styleDTO.getStock_id());
    }

    public ArrayList<Style> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM style");

        ArrayList<Style> styleDTOS = new ArrayList<>();

        while (rst.next()) {
            Style styleDTO = new Style(
                    rst.getString("style_id"),  // Style ID
                    rst.getString("size"), //size
                    rst.getInt("qty"),  // qty
                    rst.getString("employee_id"),  // employee ID
                    rst.getString("stock_id") //stock ID
            );
            styleDTOS.add(styleDTO);
        }
        return styleDTOS;
    }

    public boolean update(Style styleDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE style SET size=?, qty=?, employee_id=?, stock_id=? WHERE style_id=?",
                styleDTO.getSize(),
                styleDTO.getQty(),
                styleDTO.getEmployee_id(),
                styleDTO.getStock_id(),
                styleDTO.getStyle_id()
        );
    }

    public boolean delete(String style_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM style WHERE style_id=?", style_id);
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT style_id FROM style");

        ArrayList<String> style_ids = new ArrayList<>();

        while (rst.next()) {
            style_ids.add(rst.getString("style_id"));
        }
        return style_ids;
    }

    public Style findById(String selectedStyleId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM style WHERE style_id=?", selectedStyleId);

        ArrayList<Style> styles = new ArrayList<>();

        if (rst.next()) {
            Style style = new Style(
                    rst.getString("style_id"),  // Style ID
                    rst.getString("size"), // size
                    rst.getInt("qty"),  // Qty
                    rst.getString("employee_id"), // employee id
                    rst.getString("stock_id") // stock id
            );
            styles.add(style);
        }
        return styles.get(0);
    }
}
