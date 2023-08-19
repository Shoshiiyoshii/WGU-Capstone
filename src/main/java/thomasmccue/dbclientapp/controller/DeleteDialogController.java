package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteDialogController implements Initializable {
    @FXML
    private Button deleteButton, cancelButton;
    @FXML
    private Label confirmationDialog, toBeDeleted;

    private Appointment appointment;

    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }

    public void setToBeDeletedAppt(){
        toBeDeleted.setText("Appointment with ID: " + appointment.getApptId() +"\nAppointment Type: " + appointment.getType());
    }

    @FXML
   public void onDeleteClicked(ActionEvent event) throws IOException, SQLException {
        boolean deleted = AppointmentDao.deleteAppt(appointment);
        if (deleted) {
            confirmationDialog.setText("Appointment Deleted");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        } else {
            confirmationDialog.setText("The selected appointment could not be deleted");
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        }
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
