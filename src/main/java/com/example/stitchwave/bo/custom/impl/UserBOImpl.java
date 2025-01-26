package com.example.stitchwave.bo.custom.impl;

import com.example.stitchwave.bo.custom.UserBO;
import com.example.stitchwave.dao.DAOFactory;
import com.example.stitchwave.dao.custom.UserDAO;
import com.example.stitchwave.dto.UserDTO;
import com.example.stitchwave.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean saveUser(UserDTO DTO) throws SQLException, ClassNotFoundException {
        User user = new User(DTO.getUserId(), DTO.getFirstName(), DTO.getLastName(), DTO.getUsername(), DTO.getEmail(), DTO.getPassword());
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO DTO) throws SQLException, ClassNotFoundException {
        User user = new User(DTO.getUserId(),DTO.getFirstName(),DTO.getLastName(),DTO.getUsername(),DTO.getEmail(),DTO.getPassword());
        //System.out.println("UserBOImpl: " + user);
        return userDAO.update(user);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        return userDAO.isEmailExists(email);
    }

    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        return userDAO.getNextId();
    }

    @Override
    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = (ArrayList<User>) userDAO.getAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public ArrayList<Object> getAllUserIds() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = userDAO.getAllIds();
        ArrayList<Object> userIds = new ArrayList<>();
        for (User user : users) {
            userIds.add(user.getUserId());
        }
        return userIds;
    }

    @Override
    public User findByUserId(String selectedId) throws SQLException, ClassNotFoundException {
        return userDAO.findById(selectedId);
    }
}