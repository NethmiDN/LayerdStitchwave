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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterFormController {

    @FXML
    private JFXButton btnNext;

    @FXML
    private Label lblError;

    @FXML
    private Label lblLoggedIn;

    @FXML
    private Label lblLogin;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    public static UserDTO registeringUser = new UserDTO();

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    public UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize() {
        txtFirstName.requestFocus();
    }


    @FXML
    void btnNextOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        navigateToSecondRegisterPage();
    }

    @FXML
    void lblLoggedInOnAction(MouseEvent event) {
        loadUI("/com/example/stitchwave/LoginForm.fxml");
    }

    private void navigateToSecondRegisterPage() throws SQLException, ClassNotFoundException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty.");
        } else if (!isValidEmail(txtEmail.getText())) {
            showErrorMessage("*Invalid email format.");
        } else {
                registeringUser.setUserId(userBO.getNextUserId());
                registeringUser.setEmail(txtEmail.getText());
                registeringUser.setFirstName(txtFirstName.getText());
                registeringUser.setLastName(txtLastName.getText());
            loadUI("/com/example/stitchwave/RegisterSecondForm.fxml");
        }
    }

    private boolean areFieldsEmpty() {
        return txtEmail.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
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
