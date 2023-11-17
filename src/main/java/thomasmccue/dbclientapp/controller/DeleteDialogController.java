package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.model.Appointment;
import thomasmccue.dbclientapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class manages the delete dialog window, deleteDialog.fxml.
 * It works to delete either customers or appointments.
 */
public class DeleteDialogController implements Initializable {
    @FXML
    private Button deleteButton, cancelButton;
    @FXML
    private Label confirmationDialog, toBeDeleted;
    private Appointment appointment;
    private Customer customer;

    /**
     * This method is passed a selected customer, and sets this.customer so that it can be accessed
     * by any method that needs to. It sets the prompt message to be relevant to the selected customer
     * so that a customer isn't accidentally deleted without confirmation.
     *
     * @param customer a customer object selected from the customer table on the landing page
     */
    public void setUpCustomerDelete(Customer customer){
        this.customer = customer;

        toBeDeleted.setText("Customer with ID: " + customer.getCustomerId() + "\nCustomer Name: " + customer.getCustomerName());
    }

    /**
     * This method is passed a selected appointment, and sets this.appointment so that it can be accessed
     * by any method that needs to. It sets the prompt message to be relevant to the selected appointment
     * so that an appointment isn't accidentally deleted without confirmation.
     *
     * @param appointment an appointment object selected from the appointment table on the landing page
     */
    public void setUpAppointmentDelete(Appointment appointment){
        this.appointment = appointment;

        toBeDeleted.setText("Appointment with ID: " + appointment.getApptId() +"\nAppointment Type: " + appointment.getType());
    }

    /**
     * When the delete button it clicked to confirm the delete, the method checks
     * whether it is a customer or an appointment being deleted. Then the correct corresponding
     * Dao class and delete method are called so that the customer or appointment can be
     * deleted form the database and the display. A message is displayed showing if the
     * delete was successful or not.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
   public void onDeleteClicked(ActionEvent event) throws IOException, SQLException {
        if (this.customer != null) {
            boolean deleted = CustomerDao.deleteCust(customer);
            if(deleted){
                confirmationDialog.setText("Customer Successfully Deleted");
            } else {
                confirmationDialog.setText("Delete Failed. Please delete all associated appointments before trying again.");
            }
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        } else {
            boolean deleted = AppointmentDao.deleteAppt(appointment);
            if (deleted) {
                confirmationDialog.setText("Appointment Deleted");
            } else {
                confirmationDialog.setText("The selected appointment could not be deleted");
            }
            cancelButton.setText("Okay");
            deleteButton.setVisible(false);
        }
    }

    /**
     * This method closes the window when the cancel button is clicked.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onCancelClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
