module calc.ui {
    requires calc.core;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
    opens ui.controllers to javafx.fxml, javafx.graphics;
}
