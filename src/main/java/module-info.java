module be.kdg.demo_raycaster {
    opens be.kdg.demo_raycaster.engine to javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens be.kdg.demo_raycaster to javafx.fxml;
    exports be.kdg.demo_raycaster;
}