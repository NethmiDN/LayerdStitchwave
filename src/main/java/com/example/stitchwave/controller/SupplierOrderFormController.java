package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.SupplierBO;
import com.example.stitchwave.bo.custom.SupplierOrderBO;
import com.example.stitchwave.dto.SupplierDTO;
import com.example.stitchwave.dto.SupplierOrderDTO;
import com.example.stitchwave.entity.Supplier;
import com.example.stitchwave.entity.SupplierOrder;
import com.example.stitchwave.view.tdm.SupplierOrderTM;
import com.example.stitchwave.view.tdm.SupplierTM;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierOrderFormController implements Initializable {

        @FXML
        private ComboBox<String> cmbsupid;

        @FXML
        private TableColumn<SupplierOrderTM, LocalDate> dateclmn;

        @FXML
        private Label datelbl;

        @FXML
        private Button dltbtn;

        @FXML
        private ImageView iconimg;

        @FXML
        private TableColumn<SupplierOrderTM, String> idclmn;

        @FXML
        private Label lb;

        @FXML
        private Label lblid;

        @FXML
        private Label orderidlbl;

        @FXML
        private TableColumn<SupplierOrderTM, String> qtyclmn;

        @FXML
        private Label qtylbl;

        @FXML
        private TextField qtytxt;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private TableColumn<SupplierTM, String> supidclmn;

        @FXML
        private Label supidlbl;

        @FXML
        private Label suporderdate;

        @FXML
        private Label supplierName;

        @FXML
        private AnchorPane supplierorderap;

        @FXML
        private TableView<SupplierOrderTM> supplierordertable;

        @FXML
        private Button updatebtn;

        public SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

        public SupplierOrderBO supplierOrderBO = (SupplierOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER_ORDER);

        public void initialize(URL url, ResourceBundle resourceBundle) {
                idclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
                qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty_kg"));
                dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));
                supidclmn.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));

                try {
                        loadSupplierIds();
                        refreshPage();
                } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        private void refreshPage() throws SQLException, ClassNotFoundException {
                refreshTable();

                String nextSupplierOrderID = supplierOrderBO.getNextSupplierOrderId();
                lblid.setText(nextSupplierOrderID);

                qtytxt.setText("");
                suporderdate.setText(LocalDate.now().toString());

                savebtn.setDisable(false);
                dltbtn.setDisable(true);
                updatebtn.setDisable(true);
        }

        private void refreshTable() throws SQLException, ClassNotFoundException {
                List<SupplierOrder> supplierOrderDTOS = supplierOrderBO.getAllSupplierOrders();
                ObservableList<SupplierOrderTM> supplierOrderTMS = FXCollections.observableArrayList();

                if (supplierOrderDTOS == null || supplierOrderDTOS.isEmpty()) {
                        System.out.println("No supplier orders found.");
                        return;
                }

                for (SupplierOrder supplierOrderDTO : supplierOrderDTOS) {
                        SupplierOrderTM supplierOrderTM = new SupplierOrderTM(
                                supplierOrderDTO.getOrder_id(),
                                supplierOrderDTO.getQty_kg(),
                                supplierOrderDTO.getDate(),
                                supplierOrderDTO.getSupplier_id()
                        );
                        supplierOrderTMS.add(supplierOrderTM);
                }

                supplierordertable.setItems(supplierOrderTMS);
                supplierordertable.refresh();
        }

        @FXML
        void cmbsupidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String selectedSupId = cmbsupid.getSelectionModel().getSelectedItem();
                if (selectedSupId != null) {
                        Supplier supplierDTO = supplierBO.findBySupplierId(selectedSupId);
                        if (supplierDTO != null) {
                                supplierName.setText(supplierDTO.getName());
                        }
                }
        }

        @FXML
        void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String order_id = lblid.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Supplier Order?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {

                        boolean isDeleted = supplierOrderBO.deleteSupplierOrder(order_id);

                        if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Supplier Order deleted...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to delete Supplier Order...!").show();
                        }
                }
        }

        @FXML
        void onClickTable(MouseEvent event) {
                SupplierOrderTM selectedItem = supplierordertable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                        lblid.setText(selectedItem.getOrder_id());
                        qtytxt.setText(String.valueOf(selectedItem.getQty_kg()));
                        suporderdate.setText(String.valueOf(selectedItem.getDate()));
                        cmbsupid.setValue(selectedItem.getSupplier_id());

                        savebtn.setDisable(true);
                        dltbtn.setDisable(false);
                        updatebtn.setDisable(false);
                }
        }

        @FXML
        void resetbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                cmbsupid.setValue(null);
                cmbsupid.setPromptText("Select customer Id");

                supplierName.setText("");
                refreshPage();
        }

        @FXML
        void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
//                String order_id = lblid.getText();
//                Double qty = Double.valueOf(qtytxt.getText());
//                LocalDate date = LocalDate.parse(suporderdate.getText());
//                String supplier_id = cmbsupid.getValue();
//
//                // Define regex patterns for validation
//                String quantityPattern = "^([1-9]\\d{0,4}(\\.\\d{1,2})?|0(\\.\\d{1,2})?)$";
//
////        Validate each field using regex patterns
//                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));
//
//                // Reset input field styles
//                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");
//
//                // Highlight invalid fields in red
//
//                if (!isValidQty) {
//                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
//                }
//
//                // Save supplier order if all fields are valid
//                if (isValidQty) {
//                        SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO(order_id, qty, date, supplier_id);
//
//                        boolean isSaved = supplierOrderBO.saveOrderWithFabrics(supplierOrderDTO);
//
//                        if (isSaved) {
//                                new Alert(Alert.AlertType.INFORMATION, "Supplier Order saved...!").show();
//                                refreshPage();
//                        } else {
//                                new Alert(Alert.AlertType.ERROR, "Fail to save supplier order...!").show();
//                        }
//                }
        }

        @FXML
        void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String order_id = lblid.getText();
                Double qty = Double.valueOf(qtytxt.getText());
                LocalDate date = LocalDate.parse(suporderdate.getText());
                String supplier_id = cmbsupid.getValue();

                String quantityPattern = "^([1-9]\\d{0,4}(\\.\\d{1,2})?|0(\\.\\d{1,2})?)$";

                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

                if (!isValidQty) {
                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (isValidQty) {

                        SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO(order_id, qty, date, supplier_id);

                        boolean isUpdate = supplierOrderBO.updateSupplierOrder(supplierOrderDTO);

                        if (isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Supplier Order updated...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to update supplier order...!").show();
                        }
                }
        }

        private void loadSupplierIds() throws SQLException, ClassNotFoundException {
                ArrayList<String> supplier_ids = supplierBO.getAllSupplierIds();
                cmbsupid.setItems(FXCollections.observableArrayList(supplier_ids));
        }
}

