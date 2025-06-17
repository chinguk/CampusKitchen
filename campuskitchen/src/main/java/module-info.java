module com.campus {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    requires javafx.graphics;

    opens com.campus to javafx.fxml;
    exports com.campus;

    opens com.controllers to javafx.fxml;
    
    opens com.model to javafx.fxml;
}
