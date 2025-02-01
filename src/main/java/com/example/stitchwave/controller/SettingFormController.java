package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.UserBO;
import com.example.stitchwave.dto.UserDTO;
import com.example.stitchwave.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {

        @FXML
        private ComboBox<String> cmbuserid;

        @FXML
        private Label emaillbl;

        @FXML
        private TextField emailtxt;

        @FXML
        private Label fnamelbl;

        @FXML
        private TextField fnametxt;

        @FXML
        private ImageView iconimg;

        @FXML
        private Label lb;

        @FXML
        private Label lnamelbl;

        @FXML
        private TextField lnametxt;

        @FXML
        private Label passwordlbl;

        @FXML
        private TextField passwordtxt;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private AnchorPane settingap;

        @FXML
        private Label unamelbl;

        @FXML
        private TextField unametxt;

        @FXML
        private Label useridlbl;

    public UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize(URL url, ResourceBundle rb) {
        unametxt.requestFocus();

        try {
            loadUserIds();
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        fnametxt.setText("");
        lnametxt.setText("");
        unametxt.setText("");
        emailtxt.setText("");
        passwordtxt.setText("");

        savebtn.setDisable(false);
    }

    private void loadUserIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> userIds = userBO.getAllUserIds();
        cmbuserid.setItems(FXCollections.observableArrayList(userIds));
    }

    @FXML
        void cmbuseridOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedUserId = cmbuserid.getSelectionModel().getSelectedItem();
        selectedUserId = selectedUserId.replace("[", "").replace("]", "");
        if (selectedUserId != null) {
            User userDTO = userBO.findByUserId(selectedUserId);
            if (userDTO != null) {
                fnametxt.setText(userDTO.getFirstName());
                lnametxt.setText(userDTO.getLastName());
                unametxt.setText(userDTO.getUsername());
                emailtxt.setText(userDTO.getEmail());
                passwordtxt.setText(userDTO.getPassword());
            }
        }
        }

        @FXML
        void resetbtnOnAction(ActionEvent event) throws SQLException {
            cmbuserid.setValue(null);
            cmbuserid.setPromptText("Select user Id");

            fnametxt.setText("");
            lnametxt.setText("");
            unametxt.setText("");
            emailtxt.setText("");
            passwordtxt.setText("");

            refreshPage();
        }

        @FXML
        void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            String userId = String.valueOf(cmbuserid.getValue());
            String firstName = fnametxt.getText();
            String lastName = lnametxt.getText();
            String username = unametxt.getText();
            String email = emailtxt.getText();
            String password = passwordtxt.getText();

            String fnamePattern = "^[A-Za-z ]+$";
            String lnamePattern = "^[A-Za-z ]+$";
            String unamePattern = "^[a-zA-Z0-9_]{5,15}$";
            String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

            boolean isValidFname = firstName.matches(fnamePattern);
            boolean isValidLname = lastName.matches(lnamePattern);
            boolean isValidUname = username.matches(unamePattern);
            boolean isValidEmail = email.matches(emailPattern);
            boolean isValidPassword = password.matches(passwordPattern);

            fnametxt.setStyle(fnametxt.getStyle() + ";-fx-border-color:  #091057;");
            lnametxt.setStyle(lnametxt.getStyle() + ";-fx-border-color:  #091057;");
            unametxt.setStyle(unametxt.getStyle() + ";-fx-border-color:  #091057;");
            emailtxt.setStyle(emailtxt.getStyle() + ";-fx-border-color:  #091057;");
            passwordtxt.setStyle(passwordtxt.getStyle() + ";-fx-border-color:  #091057;");

            if(!isValidFname){
                fnametxt.setStyle(fnametxt.getStyle() + ";-fx-border-color: red;");
            }
            if(!isValidLname){
                lnametxt.setStyle(lnametxt.getStyle() + ";-fx-border-color: red;");
            }
            if(!isValidUname){
                unametxt.setStyle(unametxt.getStyle() + ";-fx-border-color: red;");
            }
            if (!isValidEmail){
                emailtxt.setStyle(emailtxt.getStyle() + ";-fx-border-color: red;");
            }
            if (!isValidPassword){
                passwordtxt.setStyle(passwordtxt.getStyle() + ";-fx-border-color: red;");
            }

            if (isValidFname && isValidLname && isValidUname && isValidEmail && isValidPassword){
                String selectedUserId = userId.replace("[", "").replace("]", "");
                UserDTO userDTO = new UserDTO(selectedUserId, firstName, lastName, username, email, password);
                boolean isSaved = userBO.updateUser(userDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "User Information saved...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save user information...!").show();
                }
            }
        }

    }