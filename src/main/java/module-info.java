module com.imrul.cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens com.imrul.cvbuilder to javafx.fxml;
    opens com.imrul.cvbuilder.controllers to javafx.fxml;
    opens com.imrul.cvbuilder.models to javafx.fxml;

    exports com.imrul.cvbuilder;
}