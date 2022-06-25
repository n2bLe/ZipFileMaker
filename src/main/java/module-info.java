module com.example.zipfilemaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.zipfilemaker to javafx.fxml;
    exports com.example.zipfilemaker;
}