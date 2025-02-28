package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.SupplierBO;
import com.example.stitchwave.dto.SupplierDTO;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private TableColumn<SupplierTM, String> contactclmn;

    @FXML
    private Label contactlbl;

    @FXML
    private TextField contacttxt;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<SupplierTM, String> idclmn;

    @FXML
    private Label lb;

    @FXML
    private Label lblid;

    @FXML
    private TableColumn<SupplierTM, String> nameclmn;

    @FXML
    private Label namelbl;

    @FXML
    private TextField nametxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Label supidlbl;

    @FXML
    private AnchorPane supplierap;

    @FXML
    private TableView<SupplierTM> suppliertable;

    @FXML
    private Button updatebtn;

    public SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        nameclmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactclmn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextSupplierID = supplierBO.getNextSupplierId();
        lblid.setText(nextSupplierID);

        nametxt.setText("");
        contacttxt.setText("");

        savebtn.setDisable(false);

        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAllSupplier();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplier_id(),
                    supplierDTO.getName(),
                    supplierDTO.getContact()
            );
            supplierTMS.add(supplierTM);
        }
        suppliertable.setItems(supplierTMS);
    }
    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplier_id = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(supplier_id);

            if (!isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM selectedItem = suppliertable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getSupplier_id());
            nametxt.setText(selectedItem.getName());
            contacttxt.setText(selectedItem.getContact());

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
        String supplier_id = lblid.getText();
        String name = nametxt.getText();
        String contact = contacttxt.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

//        Validate each field using regex patterns
        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        // Reset input field styles
        nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color:  #091057;");
        contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidName) {
            nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save supplier if all fields are valid
        if (isValidName && isValidContact) {
            SupplierDTO supplierDTO = new SupplierDTO(supplier_id, name, contact);

            boolean isSaved = supplierBO.saveSupplier(supplierDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplier_id = lblid.getText();
        String name = nametxt.getText();
        String contact = contacttxt.getText();

        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color:  #091057;");
        contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidName) {
            nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContact) {

            SupplierDTO supplierDTO = new SupplierDTO(supplier_id, name, contact);

            boolean isUpdate = supplierBO.updateSupplier(supplierDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier...!").show();
            }
        }
    }

}

