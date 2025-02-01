package com.example.stitchwave.bo.custom;

import com.example.stitchwave.bo.SuperBO;
import com.example.stitchwave.dto.UserDTO;
import com.example.stitchwave.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public boolean saveUser(UserDTO DTO) throws SQLException, ClassNotFoundException ;
    public boolean updateUser(UserDTO DTO) throws SQLException, ClassNotFoundException;
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException ;
    public String getNextUserId() throws SQLException, ClassNotFoundException ;
    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException ;
    public User findByUserId(String selectedId) throws SQLException, ClassNotFoundException ;

}