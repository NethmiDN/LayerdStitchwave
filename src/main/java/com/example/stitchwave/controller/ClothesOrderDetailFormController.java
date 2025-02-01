package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.*;
import com.example.stitchwave.dto.ClothesOrderDetailDTO;
import com.example.stitchwave.entity.Customer;
import com.example.stitchwave.entity.Payment;
import com.example.stitchwave.entity.SewnClothesStock;
import com.example.stitchwave.view.tdm.ClothesOrderDetailTM;
import com.example.stitchwave.view.tdm.CustomerTM;
import com.example.stitchwave.view.tdm.PaymentTM;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClothesOrderDetailFormController implements Initializable {

    @FXML
    private ComboBox<Object> cmbcusid;

    @FXML
    private ComboBox<Object> cmbpayid;

    @FXML
    private ComboBox<String> cmbstockid;

    @FXML
    private TableColumn<CustomerTM, String> cusidclmn;

    @FXML
    private Label cusidlbl;

    @FXML
    private Label cusnamelbl;

    @FXML
    private TableColumn<ClothesOrderDetailTM, LocalDate> dateclmn;

    @FXML
    private Label datel;

    @FXML
    private Label datelbl;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label idlbl;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<ClothesOrderDetailTM, String> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private AnchorPane ordersap;

    @FXML
    private TableView<ClothesOrderDetailTM> orderstable;

    @FXML
    private TableColumn<PaymentTM, String> paymentidclmn;

    @FXML
    private Label paymentidlbl;

    @FXML
    private Label qtlb;

    @FXML
    private TableColumn<ClothesOrderDetailTM, Integer> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Label stidlbl;

    @FXML
    private TableColumn<SewnClothesStockTM, String> stockidclmn;

    @FXML
    private Label stqtlb;

    public CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    public PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    public OrdersBO ordersBO = (OrdersBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERS);
    public ClothesOrderDetailBO clothesOrderDetailBO = (ClothesOrderDetailBO) BOFactory.getInstance().getBO(BOFactory.BOType.CLOTHES_ORDER_DETAIL);
    public SewnClothesStockBO sewnClothesStockBO = (SewnClothesStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.SEWN_CLOTHES_STOCK);

    public void initialize(URL location, ResourceBundle resources) {
        orderidclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        stockidclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
        dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cusidclmn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        paymentidclmn.setCellValueFactory(new PropertyValueFactory<>("payment_id"));

        try {
            refreshPage();
            loadStockIds();
            loadCustomerIds();
            loadPaymentIds();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextOrderID = ordersBO.getNextOrderId();
        idlbl.setText(nextOrderID);
        datelbl.setText(LocalDate.now().toString());
        qtytxt.setText("");
        qtlb.setText("");

        cmbstockid.getItems().clear();
        cmbcusid.getItems().clear();
        cmbpayid.getItems().clear();

        loadStockIds();
        loadCustomerIds();
        loadPaymentIds();

        savebtn.setDisable(false);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<ClothesOrderDetailDTO> clothesOrderDetailDTOS = clothesOrderDetailBO.getAllOrders();
        ObservableList<ClothesOrderDetailTM> clothesOrderDetailTMS = FXCollections.observableArrayList();


        for (ClothesOrderDetailDTO clothesOrderDetailDTO : clothesOrderDetailDTOS) {
            ClothesOrderDetailTM clothesOrderDetailTM = new ClothesOrderDetailTM(
                    clothesOrderDetailDTO.getOrder_id(),
                    clothesOrderDetailDTO.getStock_id(),
                    clothesOrderDetailDTO.getDate(),
                    clothesOrderDetailDTO.getQty(),
                    clothesOrderDetailDTO.getCustomer_id(),
                    clothesOrderDetailDTO.getPayment_id()
            );
            clothesOrderDetailTMS.add(clothesOrderDetailTM);
        }
        orderstable.setItems(clothesOrderDetailTMS);
    }

    private void loadStockIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> stockIds = sewnClothesStockBO.getAllStockIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(stockIds);
        cmbstockid.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIds = customerBO.getAllCustomerIds();
        ObservableList<Object> observableList = FXCollections.observableArrayList(customerIds);
        cmbcusid.setItems(observableList);
    }

    private void loadPaymentIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> paymentIds = paymentBO.getAllPaymentIds();
        ObservableList<Object> observableList = FXCollections.observableArrayList(paymentIds);
        cmbpayid.setItems(observableList);
    }


    @FXML
    void cmbcusidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedCusId = String.valueOf(cmbcusid.getSelectionModel().getSelectedItem());
        if (selectedCusId != null) {
            Customer customerDTO = customerBO.findByCustomerId(selectedCusId);
            if (customerDTO != null) {
                cusnamelbl.setText(String.valueOf(customerDTO.getName()));
            }
        } else {
            System.out.println("nanee");
        }
    }

    @FXML
    void cmbpayidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedPayId = String.valueOf(cmbpayid.getSelectionModel().getSelectedItem());
        if (selectedPayId != null) {
            Payment paymentDTO = paymentBO.findByPaymentId(selectedPayId);
        }
    }

    @FXML
    void cmbstockidOnAction(ActionEvent event) {
        String selectedStockId = String.valueOf(cmbstockid.getSelectionModel().getSelectedItem());
        if (selectedStockId != null) {
            try {
                SewnClothesStock byStockId = sewnClothesStockBO.findByStockId(selectedStockId);
                int stockQty = byStockId.getQty();
                qtlb.setText(String.valueOf(stockQty));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) throws SQLException, ClassNotFoundException {
        ClothesOrderDetailTM selectedItem = orderstable.getSelectionModel().getSelectedItem();

        ArrayList<ClothesOrderDetailDTO> selectedItemDetails = clothesOrderDetailBO.getAllOrders();

        if (selectedItem != null) {
            for (int i = 0; i < selectedItemDetails.size(); i++) {
                if (selectedItemDetails.get(i).getOrder_id().equals(selectedItem.getOrder_id())) {
                    idlbl.setText(selectedItem.getOrder_id());
                    datelbl.setText(String.valueOf(selectedItem.getDate()));
                    qtytxt.setText(String.valueOf(selectedItemDetails.get(i).getQty()));
                    cmbstockid.setValue(selectedItem.getStock_id());
                    cmbcusid.setValue(selectedItem.getCustomer_id());
                    cmbpayid.setValue(selectedItem.getPayment_id());
                    cusnamelbl.setText(customerBO.findByCustomerId(selectedItem.getCustomer_id()).getName());
                    //qtlb.setText(String.valueOf(stockModel.findById(selectedItem.getStock_id()).getQty()));
                }
            }

            savebtn.setDisable(true);
        }
    }

    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        cmbstockid.setValue(null);
        cmbstockid.setPromptText("Select stock Id");

        cmbcusid.setValue(null);
        cmbcusid.setPromptText("Select customer Id");

        cmbpayid.setValue(null);
        cmbpayid.setPromptText("Select payment Id");

        qtlb.setText("");
        qtytxt.setText("");
        cusnamelbl.setText("");
        refreshPage();
    }

    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String order_id = idlbl.getText();
        String stock_id = cmbstockid.getValue();
        int qtylblText = Integer.parseInt(qtlb.getText());
        LocalDate dateNow = LocalDate.parse(datelbl.getText());
        String customerId = (String) cmbcusid.getValue();
        String paymentId = (String) cmbpayid.getValue();
        int qtyTyped = Integer.parseInt(qtytxt.getText());

        if (cusnamelbl.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select name").show();
        } else {
            if (qtylbl.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select stock id").show();
            } else {
                boolean isSaved = clothesOrderDetailBO.saveOrderWithStockReduction(new ClothesOrderDetailTM(
                        order_id,
                        stock_id,
                        dateNow,
                        qtyTyped,
                        customerId,
                        paymentId
                ), (qtyTyped));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Clothes Order saved...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save clothes order...!").show();
                }
            }
        }
    }
}