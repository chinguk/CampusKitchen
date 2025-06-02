module com.campus {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.campus to javafx.fxml;
    exports com.campus;

    opens com.controllers to javafx.fxml;
}
