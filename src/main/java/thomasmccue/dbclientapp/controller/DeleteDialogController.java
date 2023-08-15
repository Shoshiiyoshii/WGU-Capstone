package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteDialogController implements Initializable {
    @FXML
    private Button deleteButton, cancelButton;
    @FXML
    private Label confirmationDialog, partToBeDeleted;


    @FXML
    public void onDeleteClicked(ActionEvent event) throws IOException {

    }

    @FXML
    public void onCancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
