module gui.a7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens gui.a7 to javafx.fxml;
    exports gui.a7;
}