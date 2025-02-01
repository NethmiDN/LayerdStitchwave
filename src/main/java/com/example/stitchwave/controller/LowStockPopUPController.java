package com.example.stitchwave.controller;

import com.example.stitchwave.view.tdm.FabricTM;
import com.example.stitchwave.view.tdm.SewnClothesStockTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Map;

public class LowStockPopUPController {

        @FXML
        private TableColumn<FabricTM, String> colFabric;

        @FXML
        private TableColumn<SewnClothesStockTM, Integer> colQty;

        @FXML
        private TableColumn<SewnClothesStockTM, String> colStockId;

        @FXML
        private TableView<SewnClothesStockTM> stockTable;

        private final ObservableList<SewnClothesStockTM> lowStockData = FXCollections.observableArrayList();

        @FXML
        void onCloseButtonAction(ActionEvent event) {
                Stage stage = (Stage) stockTable.getScene().getWindow();
                stage.close();
        }

        @FXML
        public void initialize() {
                colStockId.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
                colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                colFabric.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));

                stockTable.setItems(lowStockData);
        }

        public void setLowStockData(Map<String, Integer> stockItems) {
                lowStockData.clear(); // Clear existing data
                stockItems.forEach((fabricId, quantity) -> {
                        if (quantity < 5000) { // Only add items below the threshold
                                lowStockData.add(new SewnClothesStockTM(fabricId, quantity, fabricId.split("-")[0]));
                        }
                });
        }

    }

