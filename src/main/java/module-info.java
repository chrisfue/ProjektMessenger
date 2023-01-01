module de.projekt.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.projekt.gui to javafx.fxml;
    exports de.projekt.gui;
}