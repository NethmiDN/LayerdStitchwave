package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.PaymentBO;
import com.example.stitchwave.dto.PaymentDTO;
import com.example.stitchwave.view.tdm.PaymentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

        @FXML
        private TableColumn<PaymentTM, Double> amountclmn;

        @FXML
        private Label amountlbl;

        @FXML
        private TextField amounttxt;

        @FXML
        private TableColumn<PaymentTM, LocalDate> dateclmn;

        @FXML
        private Label datelbl;

        @FXML
        private Button dltbtn;

        @FXML
        private ImageView iconimg;

        @FXML
        private TableColumn<PaymentTM, String> idclmn;

        @FXML
        private Label lb;

        @FXML
        private Label lblid;

        @FXML
        private AnchorPane paymentap;

        @FXML
        private Label paymentdate;

        @FXML
        private Label paymentidlbl;

        @FXML
        private TableView<PaymentTM> paymenttable;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private Button updatebtn;

        public PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

        public void initialize(URL url, ResourceBundle resourceBundle) {
                idclmn.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
                amountclmn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));

                try {
                        refreshPage();
                } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        private void refreshPage() throws SQLException, ClassNotFoundException {
                refreshTable();

                String nextPaymentID = paymentBO.getNextPaymentId();
                lblid.setText(nextPaymentID);

                amounttxt.setText("");
                paymentdate.setText(LocalDate.now().toString());

                savebtn.setDisable(false);
                dltbtn.setDisable(true);
                updatebtn.setDisable(true);
        }

        private void refreshTable() throws SQLException, ClassNotFoundException {
                ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayment();
                ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

                for (PaymentDTO paymentDTO : paymentDTOS) {
                        PaymentTM paymentTM = new PaymentTM(
                                paymentDTO.getPayment_id(),
                                paymentDTO.getAmount(),
                                paymentDTO.getDate()
                        );
                        paymentTMS.add(paymentTM);
                }
                paymenttable.setItems(paymentTMS);
        }

        @FXML
        void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String payment_id = lblid.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {

                        boolean isDeleted = paymentBO.deletePayment(payment_id);

                        if (!isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to delete payment...!").show();
                        }
                }
        }

        @FXML
        void onClickTable(MouseEvent event) {
                PaymentTM selectedItem = paymenttable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                        lblid.setText(selectedItem.getPayment_id());
                        amounttxt.setText(String.valueOf(Double.valueOf(selectedItem.getAmount())));
                        paymentdate.setText(String.valueOf(selectedItem.getDate()));

                        savebtn.setDisable(true);

                        dltbtn.setDisable(false);
                        updatebtn.setDisable(false);
                }
        }

        @FXML
        void resetbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                refreshPage();

        }

        @FXML
        void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String payment_id = lblid.getText();
                Double amount = Double.valueOf(amounttxt.getText());
                LocalDate date = LocalDate.parse(paymentdate.getText());

                // Define regex patterns for validation
                String amountPattern = String.valueOf("^\\d{1,7}(\\.\\d{1,2})?$");

//        Validate each field using regex patterns
                boolean isValidAmount = String.valueOf(amount).matches(String.valueOf(amountPattern));

                // Reset input field styles
                amounttxt.setStyle(amounttxt.getStyle() + ";-fx-border-color:  #091057;");

                // Highlight invalid fields in red

                if (!isValidAmount) {
                        amounttxt.setStyle(amounttxt.getStyle() + ";-fx-border-color: red;");
                }

                // Save payment if all fields are valid
                if (isValidAmount) {
                        PaymentDTO paymentDTO = new PaymentDTO(payment_id, amount, date);

                        boolean isSaved = paymentBO.savePayment(paymentDTO);

                        if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Payment saved...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to save payment...!").show();
                        }
                }
        }

        @FXML
        void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String payment_id = lblid.getText();
                Double amount = Double.valueOf(amounttxt.getText());
                LocalDate date = LocalDate.parse(paymentdate.getText());

                String amountPattern = String.valueOf("^\\d{1,7}(\\.\\d{1,2})?$");

                boolean isValidAmount = String.valueOf(amount).matches(String.valueOf(amountPattern));

                amounttxt.setStyle(amounttxt.getStyle() + ";-fx-border-color:  #091057;");

                if (!isValidAmount) {
                        amounttxt.setStyle(amounttxt.getStyle() + ";-fx-border-color: red;");
                }

                if (isValidAmount) {

                        PaymentDTO paymentDTO = new PaymentDTO(payment_id, amount, date);

                        boolean isUpdate = paymentBO.updatePayment(paymentDTO);

                        if (isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Payment updated...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to update payment...!").show();
                        }
                }
        }

    }
