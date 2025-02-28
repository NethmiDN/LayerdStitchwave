package com.example.stitchwave.controller;

import com.example.stitchwave.bo.BOFactory;
import com.example.stitchwave.bo.custom.SewnClothesStockBO;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button clothesorderdetailbtn;

    @FXML
    private Button customerbtn;

    @FXML
    private Button empbtn;

    @FXML
    private Button fabricbtn;

    @FXML
    private ImageView homebtn;

    @FXML
    private Button notificationbtn;

    @FXML
    private Button paymentbtn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView settingbtn;

    @FXML
    private Button sewnclothesstockbtn;

    @FXML
    private Button stylebtn;

    @FXML
    private Button supplierbtn;

    @FXML
    private Button supplierorderbtn;

    private ScheduledExecutorService scheduler;

    public SewnClothesStockBO sewnClothesStockBO = (SewnClothesStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.SEWN_CLOTHES_STOCK);


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        navigateTo("/com/example/stitchwave/Home.fxml");
        initializeButtonEffect(empbtn);
        initializeButtonEffect(customerbtn);
        initializeButtonEffect(paymentbtn);
        initializeButtonEffect(clothesorderdetailbtn);
        initializeButtonEffect(fabricbtn);
        initializeButtonEffect(stylebtn);
        initializeButtonEffect(sewnclothesstockbtn);
        initializeButtonEffect(supplierbtn);
        initializeButtonEffect(supplierorderbtn);

        notificationbtn.setVisible(false);
        onButtonClicked(empbtn);
        setButtonSizes();

        // Initialize the scheduler for periodic stock checks
        startStockCheckScheduler();
    }

    private void startStockCheckScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkQtyAvaialbility();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @FXML
    private void checkQtyAvaialbility() {
        try {
            Map<String, Integer> lowStockItems = sewnClothesStockBO.getLowStockItems(5000);

            if (!lowStockItems.isEmpty()) {
                StringBuilder alertText = new StringBuilder("⚠️ Low Stock Alert: ");
                for (Map.Entry<String, Integer> entry : lowStockItems.entrySet()) {
                    alertText.append(String.format("[%s: %d] ", entry.getKey(), entry.getValue()));
                }

                notificationbtn.setText(alertText.toString().trim());
                if (!notificationbtn.isVisible()) {
                    notificationbtn.setVisible(true);
                    showNotificationAnimation(notificationbtn);
                }
            } else {
                notificationbtn.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to check stock quantities!").show();
        }
    }

    private void showNotificationAnimation(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), node);
        scaleTransition.setFromX(0.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.play();
    }

    @FXML
    void clothesorderdetailbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/ClothesOrderDetailForm.fxml");
        onButtonClicked(clothesorderdetailbtn);
    }

    @FXML
    void customerbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/CustomerForm.fxml");
        onButtonClicked(customerbtn);
    }

    @FXML
    void empbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/EmployeeForm.fxml");
        onButtonClicked(empbtn);
    }

    @FXML
    void fabricbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/FabricOrderForm.fxml");
        onButtonClicked(fabricbtn);
    }

    @FXML
    void homebtnOnAction(MouseEvent event) {
        navigateTo("/com/example/stitchwave/Home.fxml");
        onButtonClickeda(homebtn);
    }

    @FXML
    void notificationbtnOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stitchwave/LowStockPopUp.fxml"));
            AnchorPane popupContent = loader.load();

            LowStockPopUPController popupController = loader.getController();

            popupController.setLowStockData(sewnClothesStockBO.getLowStockItems(5000));

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Low Stock Details");

            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load popup!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error occurred!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    @FXML
    private void initializeButtonEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(100, 100, 100, 0.4));

        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }

    private void setButtonSizes() {
        double buttonWidth = 240;
        double buttonHeight = 20;

        empbtn.setPrefSize(buttonWidth, buttonHeight);
        customerbtn.setPrefSize(buttonWidth, buttonHeight);
        paymentbtn.setPrefSize(buttonWidth, buttonHeight);
        clothesorderdetailbtn.setPrefSize(buttonWidth, buttonHeight);
        fabricbtn.setPrefSize(buttonWidth, buttonHeight);
        stylebtn.setPrefSize(buttonWidth, buttonHeight);
        sewnclothesstockbtn.setPrefSize(buttonWidth, buttonHeight);
        supplierbtn.setPrefSize(buttonWidth, buttonHeight);
        supplierorderbtn.setPrefSize(buttonWidth, buttonHeight);
    }

    private void setActiveButtonStyle(Button activeButton) {
        activeButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #1FA2FF, #1885D2);" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-border-color: #1885D2;" +
                        "-fx-border-width: 1;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-effect: dropshadow(gaussian, rgba(24, 133, 210, 0.4), 12, 0, 0, 3);"
        );
    }

    private void resetButtonStyles() {
        String resetStyle =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: linear-gradient(to right, #1FA2FF, #1885D2);" +
                        "-fx-border-width: 1;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-text-fill: #1885D2;";

        empbtn.setStyle(resetStyle);
        customerbtn.setStyle(resetStyle);
        paymentbtn.setStyle(resetStyle);
        clothesorderdetailbtn.setStyle(resetStyle);
        fabricbtn.setStyle(resetStyle);
        stylebtn.setStyle(resetStyle);
        sewnclothesstockbtn.setStyle(resetStyle);
        supplierbtn.setStyle(resetStyle);
        supplierorderbtn.setStyle(resetStyle);
    }

    private void onButtonClicked(Button selectedButton) {
        resetButtonStyles();
        setActiveButtonStyle(selectedButton);
    }

    private void onButtonClickeda(ImageView selectedButton) {
        resetButtonStyles();
    }

    @FXML
    void paymentbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/PaymentForm.fxml");
        onButtonClicked(paymentbtn);
    }

    @FXML
    void settingbtnOnAction(MouseEvent event) {
        navigateTo("/com/example/stitchwave/SettingForm.fxml");
        onButtonClickeda(settingbtn);
    }

    @FXML
    void sewnclothesstockbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/SewnClothesStockForm.fxml");
        onButtonClicked(sewnclothesstockbtn);
    }

    @FXML
    void stylebtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/StyleForm.fxml");
        onButtonClicked(stylebtn);
    }

    @FXML
    void supplierbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/SupplierForm.fxml");
        onButtonClicked(supplierbtn);
    }

    @FXML
    void supplierorderbtnOnAction(ActionEvent event) {
        navigateTo("/com/example/stitchwave/SupplierOrderForm.fxml");
        onButtonClicked(supplierorderbtn);
    }

    private void navigateTo(String fxmlPath) {
        try {
            ap.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(ap.widthProperty());
            load.prefHeightProperty().bind(ap.heightProperty());
            ap.getChildren().add(load);

            applyEnhancedTransition(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    private void applyEnhancedTransition(Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), node);
        scaleTransition.setFromX(0.85);
        scaleTransition.setFromY(0.85);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(400), node);
        slideTransition.setFromY(20);
        slideTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, scaleTransition, slideTransition);
        parallelTransition.play();
    }
}