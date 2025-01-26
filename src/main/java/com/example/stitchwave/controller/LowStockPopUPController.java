package com.example.stitchwave.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LowStockPopUPController {

        @FXML
        private TableColumn<?, ?> colFabric;

        @FXML
        private TableColumn<?, ?> colQty;

        @FXML
        private TableColumn<?, ?> colStockId;

        @FXML
        private TableView<?> stockTable;

        @FXML
        void onCloseButtonAction(ActionEvent event) {

        }

    }

