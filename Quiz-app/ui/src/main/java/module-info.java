module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;

    opens ui to javafx.graphics, javafx.fxml;
    opens ui.controllers to javafx.fxml, javafx.graphics;
}
