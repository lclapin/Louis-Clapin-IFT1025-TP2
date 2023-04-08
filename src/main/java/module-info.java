module server.clapin_louis {
    requires javafx.controls;
    requires javafx.fxml;


    opens server.clapin_louis to javafx.fxml;
    exports server.clapin_louis;
}