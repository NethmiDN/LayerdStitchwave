package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.UserBO;
import com.example.stitchwave.dto.UserDTO;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private ImageView imgPasswordView;

    @FXML
    private Label lblCreateNewAccount;

    @FXML
    private Label lblError;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblLogin2;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPasswordVisible;

    @FXML
    private TextField txtUsername;

    private boolean isPasswordVisible = false;

    public UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (checkUsernameAndPassword()) {
            loadUI("/com/example/stitchwave/DashboardForm.fxml");
        } else {
            showErrorMessage("Incorrect username or password. Please try again.");
        }
    }

    private boolean checkUsernameAndPassword() throws SQLException, ClassNotFoundException {
        List<UserDTO> allUsers = userBO.getAllUser();

        for (UserDTO user : allUsers) {
            if (user.getUsername().equals(txtUsername.getText()) && user.getPassword().equals(txtPassword.getText())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void imgPasswordViewOnAction(MouseEvent event) {
        if (isPasswordVisible) {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPasswordVisible.setVisible(false);
            txtPassword.setVisible(true);
        } else {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPassword.setVisible(false);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    void lblCreateNewAccountOnAction(MouseEvent event) {
        loadUI("/com/example/stitchwave/RegisterForm.fxml");

    }

    @FXML
    void lblForgotPasswordOnAction(MouseEvent event) {
        loadUI("/com/example/stitchwave/ForgetPasswordForm.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsername.requestFocus();

        bodyPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                btnLogin.fire();
            }
        });
    }

    private void loadUI(String resource) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        lblError.setText(message);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }
}
