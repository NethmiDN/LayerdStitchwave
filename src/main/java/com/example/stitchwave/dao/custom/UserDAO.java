package com.example.stitchwave.dao.custom;

import com.example.stitchwave.dao.CrudDAO;
import com.example.stitchwave.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException ;
}
