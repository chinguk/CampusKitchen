module com.campus {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.campus to javafx.fxml;
    exports com.campus;
}
