module com.imrul.cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires com.google.gson;

    opens com.imrul.cvbuilder to javafx.fxml;
    opens com.imrul.cvbuilder.controllers to javafx.fxml;
    opens com.imrul.cvbuilder.models to javafx.fxml, com.google.gson;
    opens com.imrul.cvbuilder.database to com.google.gson;
    opens com.imrul.cvbuilder.utils to com.google.gson;

    exports com.imrul.cvbuilder;
    exports com.imrul.cvbuilder.models;
    exports com.imrul.cvbuilder.utils;
}