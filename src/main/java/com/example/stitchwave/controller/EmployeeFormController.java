package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.EmployeeBO;
import com.example.stitchwave.dto.EmployeeDTO;
import com.example.stitchwave.view.tdm.EmployeeTM;
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

public class EmployeeFormController implements Initializable {

        @FXML
        private TableColumn<EmployeeTM, String> contactclmn;

        @FXML
        private Label contactlbl;

        @FXML
        private TextField contacttxt;

        @FXML
        private Button dltbtn;

        @FXML
        private Label empidlbl;

        @FXML
        private AnchorPane employeeap;

        @FXML
        private TableView<EmployeeTM> emptable;

        @FXML
        private ImageView iconimg;

        @FXML
        private TableColumn<EmployeeTM, String> idclmn;

        @FXML
        private Label lb;

        @FXML
        private Label lblid;

        @FXML
        private TableColumn<EmployeeTM, String> nameclmn;

        @FXML
        private Label namelbl;

        @FXML
        private TextField nametxt;

        @FXML
        private Button resetbtn;

        @FXML
        private Button savebtn;

        @FXML
        private Button updatebtn;

    public EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
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

        String nextEmployeeID = employeeBO.getNextEmployeeId();
        lblid.setText(nextEmployeeID);

        nametxt.setText("");
        contacttxt.setText("");

        savebtn.setDisable(false);

        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployee();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployee_id(),
                    employeeDTO.getName(),
                    employeeDTO.getContact()
            );
            employeeTMS.add(employeeTM);
        }
        emptable.setItems(employeeTMS);
    }
    @FXML
        void dltbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeBO.deleteEmployee(employeeId);

            if (!isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete employee...!").show();
            }
        }
        }

        @FXML
        void onClickTable(MouseEvent event) {
            EmployeeTM selectedItem = emptable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                lblid.setText(selectedItem.getEmployee_id());
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
            String employeeId = lblid.getText();
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

            // Save employee if all fields are valid
            if (isValidName && isValidContact) {
                EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, contact);

                boolean isSaved = employeeBO.saveEmployee(employeeDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save employee...!").show();
                }
            }
        }

        @FXML
        void updatebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            String employeeId = lblid.getText();
            String name = nametxt.getText();
            String contact = contacttxt.getText();

            String namePattern = "^[A-Za-z ]+$";
            String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

            boolean isValidName = name.matches(namePattern);
            boolean isValidContact = contact.matches(contactPattern);

            nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: #091057;");
            contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color:  #091057;");

            if (!isValidName) {
                nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: red;");
            }

            if (!isValidContact) {
                contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color: red;");
            }

            if (isValidName && isValidContact) {

                EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, contact);

                boolean isUpdate = employeeBO.updateEmployee(employeeDTO);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update employee...!").show();
                }
            }
        }

    }

