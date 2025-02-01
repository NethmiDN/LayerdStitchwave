package com.example.stitchwave.controller;


import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.EmployeeBO;
import com.example.stitchwave.bo.custom.SewnClothesStockBO;
import com.example.stitchwave.bo.custom.StyleBO;
import com.example.stitchwave.dto.StyleDTO;
import com.example.stitchwave.entity.Employee;
import com.example.stitchwave.entity.SewnClothesStock;
import com.example.stitchwave.view.tdm.EmployeeTM;
import com.example.stitchwave.view.tdm.SewnClothesStockTM;
import com.example.stitchwave.view.tdm.StyleTM;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StyleFormController implements Initializable {

        @FXML
        private ComboBox<String> cmbempid;

        @FXML
        private ComboBox<String> cmbstockid;

        @FXML
        private Button dltbtn;

        @FXML
        private TableColumn<EmployeeTM, String> empidclmn;

        @FXML
        private Label empidlbl;

        @FXML
        private Label employeenamelbl;

        @FXML
        private ImageView iconimg;

        @FXML
        private Label idlbl;

        @FXML
        private Label lb;

        @FXML
        private TableColumn<StyleTM, Integer> qtyclmn;

        @FXML
        private Label qtylbl;

        @FXML
        private TextField qtytxt;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private TableColumn<StyleTM, String> sizeclmn;

        @FXML
        private Label sizelbl;

        @FXML
        private TextField sizetxt;

        @FXML
        private TableColumn<SewnClothesStockTM, String> stockidclmn;

        @FXML
        private Label stockidlbl;

        @FXML
        private AnchorPane styleap;

        @FXML
        private TableColumn<StyleTM, String> styleidclmn;

        @FXML
        private Label styleidlbl;

        @FXML
        private TableView<StyleTM> styletable;

        @FXML
        private Button updatebtn;

        public StyleBO styleBO = (StyleBO) BOFactory.getInstance().getBO(BOFactory.BOType.STYLE);

        public EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

        public SewnClothesStockBO sewnClothesStockBO = (SewnClothesStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.SEWN_CLOTHES_STOCK);

        public void initialize(URL url, ResourceBundle resourceBundle) {
                styleidclmn.setCellValueFactory(new PropertyValueFactory<>("style_id"));
                sizeclmn.setCellValueFactory(new PropertyValueFactory<>("size"));
                qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
                empidclmn.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                stockidclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));

                try {
                        loademployee_ids();
                        loadStockIds();
                        refreshPage();
                } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        private void refreshPage() throws SQLException, ClassNotFoundException {
                refreshTable();

                String nextStyleID = styleBO.getNextStyleId();
                idlbl.setText(nextStyleID);
                sizetxt.setText("");
                qtytxt.setText("");

                savebtn.setDisable(false);
                dltbtn.setDisable(true);
                updatebtn.setDisable(true);
        }

        private void refreshTable() throws SQLException, ClassNotFoundException {
                ArrayList<StyleDTO> styleDTOS = styleBO.getAllStyle();
                ObservableList<StyleTM> styleTMS = FXCollections.observableArrayList();

                for (StyleDTO styleDTO : styleDTOS) {
                        StyleTM styleTM = new StyleTM(
                                styleDTO.getStyle_id(),
                                styleDTO.getSize(),
                                styleDTO.getQty(),
                                styleDTO.getEmployee_id(),
                                styleDTO.getStock_id()
                        );
                        styleTMS.add(styleTM);
                }
                styletable.setItems(styleTMS);
        }
        @FXML
        void cmbempidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String selectedEmpId = cmbempid.getSelectionModel().getSelectedItem();
                if (selectedEmpId != null) {
                        Employee employeeDTO = employeeBO.findByEmployeeId(selectedEmpId);
                        if (employeeDTO != null) {
                                employeenamelbl.setText(employeeDTO.getName());
                        }
                }
        }

        private void loadStockIds() throws SQLException, ClassNotFoundException {
                ArrayList<String> stockIds = sewnClothesStockBO.getAllStockIds();
                ObservableList<String> observableList = FXCollections.observableArrayList();
                observableList.addAll(stockIds);
                cmbstockid.setItems(observableList);
        }

        @FXML
        void cmbstockidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String selectedStockId = cmbstockid.getSelectionModel().getSelectedItem();
                if (selectedStockId != null) {
                        SewnClothesStock sewnClothesStockDTO = sewnClothesStockBO.findByStockId(selectedStockId);
                }
        }

        @FXML
        void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String style_id = idlbl.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Style?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {

                        boolean isDeleted = styleBO.deleteStyle(style_id);

                        if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Style deleted...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to delete Style...!").show();
                        }
                }
        }

        @FXML
        void onClickTable(MouseEvent event) {
                StyleTM selectedItem = styletable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                        idlbl.setText(selectedItem.getStyle_id());
                        sizetxt.setText(selectedItem.getSize());
                        qtytxt.setText(String.valueOf(selectedItem.getQty()));

                        cmbempid.setValue(selectedItem.getEmployee_id());
                        cmbstockid.setValue(selectedItem.getStock_id());

                        savebtn.setDisable(true);
                        dltbtn.setDisable(false);
                        updatebtn.setDisable(false);
                }
        }

        @FXML
        void resetbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                cmbempid.setValue(null);
                cmbempid.setPromptText("Select customer Id");

                cmbstockid.setValue(null);
                cmbstockid.setPromptText("Select payment Id");

                employeenamelbl.setText("");
                refreshPage();
        }

        @FXML
        void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String style_id = idlbl.getText();
                String size = sizetxt.getText();
                Integer qty = Integer.valueOf(qtytxt.getText());
                String employee_id = cmbempid.getValue();
                String stock_id = cmbstockid.getValue();

                // Define regex patterns for validation
                String sizePattern = "(?i)^(xs|s|m|l|xl|xxl|xxxl)$";
                String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        Validate each field using regex patterns
                boolean isValidSize = size.matches(sizePattern);
                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

                // Reset input field styles
                sizetxt.setStyle(sizetxt.getStyle() + ";-fx-border-color:  #091057;");
                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

                // Highlight invalid fields in red

                if (!isValidSize) {
                        sizetxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (!isValidQty) {
                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                // Save style if all fields are valid
                if (isValidSize && isValidQty) {
                        StyleDTO styleDTO = new StyleDTO(style_id, size, qty, employee_id, stock_id);

                        boolean isSaved = styleBO.saveStyle(styleDTO);

                        if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Style saved...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to save style...!").show();
                        }
                }
        }

        @FXML
        void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String style_id = idlbl.getText();
                String size = sizetxt.getText();
                Integer qty = Integer.valueOf(qtytxt.getText());
                String employee_id = cmbempid.getValue();
                String stock_id = cmbstockid.getValue();

                String sizePattern = "(?i)^(xs|s|m|l|xl|xxl|xxxl)$";
                String quantityPattern = "^([1-9]\\d{0,4}|0)$";

                boolean isValidSize = size.matches(sizePattern);
                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

                sizetxt.setStyle(sizetxt.getStyle() + ";-fx-border-color:  #091057;");
                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

                if (!isValidSize) {
                        sizetxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (!isValidQty) {
                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (isValidSize && isValidQty) {

                        StyleDTO styleDTO = new StyleDTO(style_id, size, qty, employee_id, stock_id);

                        boolean isUpdate = styleBO.updateStyle(styleDTO);

                        if (isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Style updated...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to update style...!").show();
                        }

                }

        }

        private void loademployee_ids() throws SQLException, ClassNotFoundException {
                ArrayList<String> employeeIds = employeeBO.getAllEmployeeIds();
                ObservableList<String> observableList = FXCollections.observableArrayList();
                observableList.addAll(employeeIds);
                cmbempid.setItems(observableList);
        }
}
