module com.maria.kittengrowthcurve {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.maria.kittengrowthcurve to javafx.fxml;
    exports com.maria.kittengrowthcurve;
}