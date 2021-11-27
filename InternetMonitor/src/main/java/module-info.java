module com.example.internetmonitor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.colbymeline.internetmonitor to javafx.fxml;
    exports com.colbymeline.internetmonitor;
}