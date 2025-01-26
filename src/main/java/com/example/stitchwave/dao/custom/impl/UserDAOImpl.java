package com.example.stitchwave.dao.custom.impl;

import com.example.stitchwave.dao.SQLUtil;
import com.example.stitchwave.dao.custom.UserDAO;
import com.example.stitchwave.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user VALUES (userId,firstName,lastName,username,email,password)",DTO.getUserId(),DTO.getFirstName(),DTO.getLastName(),DTO.getUsername(),DTO.getEmail(),DTO.getPassword());
    }

    @Override
    public boolean update(User DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET password = ? where email = ?",DTO.getPassword(),DTO.getEmail());
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT email FROM user WHERE email = ?",email);
        return rst.next();
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT userId FROM user ORDER BY userId DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT *FROM user");

        ArrayList<User> userList = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString("userId"),
                    rst.getString("firstName"),
                    rst.getString("lastName"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("password")
            );
            userList.add(user);
        }
        return userList;
    }

    @Override
    public ArrayList<User> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT userId FROM user");

        ArrayList<User> userList = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString("userId"),
                    rst.getString("firstName"),
                    rst.getString("lastName"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("password")
            );
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User findById(String selectedId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE userId=?",selectedId);
        ArrayList<User> userDTOS = new ArrayList<>();
        while (rst.next()){
            User user = new User(
                    rst.getString("userId"),
                    rst.getString("firstName"),
                    rst.getString("lastName"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("password")
            );
            userDTOS.add(user);
        }
        return userDTOS.get(0);
    }
}