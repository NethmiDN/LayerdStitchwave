package com.example.stitchwave;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("STITCHWAVE");
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stitchwave/LoginForm.fxml")))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assests/images/3dmachine.png"))));
        stage.show();
    }
}
