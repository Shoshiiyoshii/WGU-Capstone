module thomasmccue.dbclientappreal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens thomasmccue.dbclientapp to javafx.fxml;
    exports thomasmccue.dbclientapp;
    exports thomasmccue.dbclientapp.helper;
    opens thomasmccue.dbclientapp.helper to javafx.fxml;
    exports thomasmccue.dbclientapp.controller;
    opens thomasmccue.dbclientapp.controller to javafx.fxml;
    exports thomasmccue.dbclientapp.model;
    opens thomasmccue.dbclientapp.model to javafx.fxml;
}