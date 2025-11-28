module eglv.sistemagerenciamentoacervos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens eglv.sistemagerenciamentoacervos to javafx.fxml;

    exports eglv.sistemagerenciamentoacervos;
}