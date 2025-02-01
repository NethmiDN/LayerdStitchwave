package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.FabricBO;
import com.example.stitchwave.bo.custom.SewnClothesStockBO;
import com.example.stitchwave.dto.FabricDTO;
import com.example.stitchwave.dto.SewnClothesStockDTO;
import com.example.stitchwave.entity.Fabric;
import com.example.stitchwave.view.tdm.SewnClothesStockTM;
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

public class SewnClothesStockFormController implements Initializable {

        @FXML
        private ComboBox<String> cmbfabid;

        @FXML
        private Button dltbtn;

        @FXML
        private TableColumn<?, String> fabidclmn;

        @FXML
        private Label fabidlbl;

        @FXML
        private ImageView iconimg;

        @FXML
        private TableColumn<SewnClothesStockTM, String> idclmn;

        @FXML
        private Label lb;

        @FXML
        private Label lblid;

        @FXML
        private TableColumn<SewnClothesStockTM, String> qtyclmn;

        @FXML
        private Label qtylbl;

        @FXML
        private TextField qtytxt;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private AnchorPane sewnclothesstockap;

        @FXML
        private TableView<SewnClothesStockTM> sewnclothesstocktable;

        @FXML
        private Label stockidlbl;

        @FXML
        private Button updatebtn;

        public FabricBO fabricBO = (FabricBO) BOFactory.getInstance().getBO(BOFactory.BOType.FABRIC);

        public SewnClothesStockBO sewnClothesStockBO = (SewnClothesStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.SEWN_CLOTHES_STOCK);

        public void initialize(URL url, ResourceBundle resourceBundle) {
                idclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
                qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
                fabidclmn.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));

                try {
                        loadFabricIds();
                        refreshPage();
                } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        private void refreshPage() throws SQLException, ClassNotFoundException {
                refreshTable();

                String nextStockID = sewnClothesStockBO.getNextSewnClothesStockId();
                lblid.setText(nextStockID);

                qtytxt.setText("");

                savebtn.setDisable(false);
                dltbtn.setDisable(true);
                updatebtn.setDisable(true);
        }

        private void refreshTable() throws SQLException, ClassNotFoundException {
                ArrayList<SewnClothesStockDTO> sewnClothesStockDTOS = sewnClothesStockBO.getAllSewnClothesStock();
                ObservableList<SewnClothesStockTM> sewnClothesStockTMS = FXCollections.observableArrayList();

                for (SewnClothesStockDTO sewnClothesStockDTO : sewnClothesStockDTOS) {
                        SewnClothesStockTM sewnClothesStockTM = new SewnClothesStockTM(
                                sewnClothesStockDTO.getStock_id(),
                                sewnClothesStockDTO.getQty(),
                                sewnClothesStockDTO.getFabric_id()
                        );
                        sewnClothesStockTMS.add(sewnClothesStockTM);
                }
                sewnclothesstocktable.setItems(sewnClothesStockTMS);
        }

        @FXML
        void cmbfabidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String selectedFabId = String.valueOf(cmbfabid.getSelectionModel().getSelectedItem());
                if (selectedFabId != null) {
                        Fabric fabricDTO = fabricBO.findByFabricId(selectedFabId);

                }
        }

        @FXML
        void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String stock_id = lblid.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Stock?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {

                        boolean isDeleted = sewnClothesStockBO.deleteStock(stock_id);

                        if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Stock deleted...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to delete Stock...!").show();
                        }
                }
        }

        @FXML
        void onClickTable(MouseEvent event) {
                SewnClothesStockTM selectedItem = sewnclothesstocktable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                        lblid.setText(selectedItem.getStock_id());
                        qtytxt.setText(String.valueOf(selectedItem.getQty()));
                        cmbfabid.setValue(selectedItem.getFabric_id()); // Set ComboBox to the correct fabric ID

                        savebtn.setDisable(true);
                        dltbtn.setDisable(false);
                        updatebtn.setDisable(false);
                }
        }

        @FXML
        void resetbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                cmbfabid.setValue(null);
                cmbfabid.setPromptText("Select customer Id");

                refreshPage();
        }

        @FXML
        void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String stock_id = lblid.getText();
                Integer qty = Integer.valueOf(qtytxt.getText());
                String fabric_id = cmbfabid.getValue();

                String quantityPattern = "^([1-9]\\d{0,4}|0)$";

                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");


                if (!isValidQty) {
                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (isValidQty) {
                        SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO(stock_id, qty, fabric_id);

                        boolean isSaved = sewnClothesStockBO.saveSewnClothesStock(sewnClothesStockDTO);

                        if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Stock saved...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to save stock...!").show();
                        }
                }
        }

        @FXML
        void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String stock_id = lblid.getText();
                Integer qty = Integer.valueOf(qtytxt.getText());
                String fabric_id = cmbfabid.getValue();

                String quantityPattern = "^([1-9]\\d{0,4}|0)$";

                boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

                qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

                if (!isValidQty) {
                        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
                }

                if (isValidQty) {

                        SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO(stock_id, qty, fabric_id);

                        boolean isUpdate = sewnClothesStockBO.updateSewnClothesStock(sewnClothesStockDTO);

                        if (isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Stock updated...!").show();
                                refreshPage();
                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to update stock...!").show();
                        }
                }
        }

        private void loadFabricIds() throws SQLException, ClassNotFoundException {
                ArrayList<String> fabricIds = fabricBO.getAllFabricIds();
                cmbfabid.getItems().addAll(fabricIds);
        }


}

