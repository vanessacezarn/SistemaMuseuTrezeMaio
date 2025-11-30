module eglv.sistemagerenciamentoacervos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens eglv.sistemagerenciamentoacervos to javafx.fxml;
    opens eglv.sistemagerenciamentoacervos.controller to javafx.fxml;

    exports eglv.sistemagerenciamentoacervos;

}