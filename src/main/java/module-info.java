module com.example.stitchwave {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;
    requires lombok;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires javafx.graphics;
    requires org.checkerframework.checker.qual;

    opens com.example.stitchwave to javafx.fxml;
    opens com.example.stitchwave.controller to javafx.fxml;
    opens com.example.stitchwave.view.tdm to javafx.base;

    exports com.example.stitchwave;
    exports com.example.stitchwave.controller;
}