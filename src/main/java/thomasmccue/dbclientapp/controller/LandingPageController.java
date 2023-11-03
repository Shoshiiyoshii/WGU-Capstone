package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.Main;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static thomasmccue.dbclientapp.helper.JDBC.connection;

public class LandingPageController implements Initializable {
    @FXML
    private Label ynUpcomingAppointment, nextAppt, nextApptWith, whenNext, whoNext, title, customersTitle, customerErrorMessage, apptErrorMessage;
    @FXML
    private TableView<Customer> custTable;
    @FXML
    private TableColumn<Customer, Integer> custIdBottomCol, divisionIdCol;
    @FXML
    private TableColumn<Customer, String> nameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdated, lastUpdatedByCol, countryCol;
    @FXML
    private Button custAddButton, custUpdateButton, custDeleteButton, apptAddButton1, apptUpdateButton, apptDeleteButton,exitButton, apptReportButton, contactSchedulesButton, custRecordCreationReportButton;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol, contactIdCol, custIdTopCol, userIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol, descCol, locationCol, typeCol, startDTCol, endDTCol;
    @FXML
    private RadioButton weekRadio, monthRadio, allTimeRadio;

    public void clickCustAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrUpdateCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AddOrUpdateCustomerController controller = fxmlLoader.getController();
        controller.setUpAdd("Add A Customer To The System", "Save");
        stage.setTitle("Add A Customer To The System");
        stage.setScene(scene);
        stage.show();
    }
    public void clickCustUpdate(ActionEvent event) throws IOException {
        SelectionModel<Customer> selectionModel = custTable.getSelectionModel();
        Customer selectedCust = selectionModel.getSelectedItem();

        if(selectedCust == null){
            customerErrorMessage.setText("Please first select a Customer to Modify.");
        } else{
            customerErrorMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrUpdateCustomer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AddOrUpdateCustomerController controller = fxmlLoader.getController();
            controller.setUpModify("Modify An Existing Customer", "Save Update", selectedCust);
            stage.setTitle("Modify An Existing Customer");
            stage.setScene(scene);
            stage.show();
        }

    }
    public void clickCustDelete(ActionEvent event){

    }

    public void radioFilter(ActionEvent event){
        if(monthRadio.isSelected()){
           // System.out.println(AppointmentDao.getThisMonthsAppointments());FIXME
            apptTable.setItems(AppointmentDao.getThisMonthsAppointments());
        } else if (weekRadio.isSelected()) {
           // System.out.println(AppointmentDao.getThisWeeksAppointments());FIXME
            apptTable.setItems(AppointmentDao.getThisWeeksAppointments());
        }else if(allTimeRadio.isSelected()){
           // System.out.println(AppointmentDao.getAllAppointments());FIXME
            apptTable.setItems(AppointmentDao.getAllAppointments());
        }
    }
    public void clickApptAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrModifyAppointmentPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AddOrUpdateAppointmentController controller = fxmlLoader.getController();
        controller.setUpAdd("Create a new Appointment", "Save");
        stage.setTitle("Create A New Appointment");
        stage.setScene(scene);
        stage.show();
    }
    public void clickApptUpdate(ActionEvent event) throws IOException{
        SelectionModel<Appointment> selectionModel = apptTable.getSelectionModel();
        Appointment selectedAppt = selectionModel.getSelectedItem();

        if(selectedAppt == null) {
            apptErrorMessage.setText("Please select an existing appointment to modify.");

        } else {
            apptErrorMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrModifyAppointmentPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AddOrUpdateAppointmentController controller = fxmlLoader.getController();
            int apptID = selectedAppt.getApptId();
            controller.setUpModify("Update An Existing Appointment", "Save Changes", apptID);
            controller.preFillFields(selectedAppt);

            stage.setTitle("Update Existing Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void clickApptDelete(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("deleteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Delete Appointment");
        stage.setScene(scene);

      DeleteDialogController dialogController = fxmlLoader.getController();

        SelectionModel<Appointment> selectionModel = apptTable.getSelectionModel();
        Appointment selectedAppt = selectionModel.getSelectedItem();
        if(selectedAppt != null) {
            apptErrorMessage.setText("");
            dialogController.setAppointment(selectedAppt);
            dialogController.setToBeDeletedAppt();

            stage.show();
        } else{
            apptErrorMessage.setText("First select an appointment to delete.");
        }
    }

    public void exitClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

/*    public static void refreshCustomersTable() {
        // Fetch the latest customer data from the database and update the tableView
        custTable.setItems(CustomerDao.getAllCust());


    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custTable.setItems(CustomerDao.getAllCust());

        custIdBottomCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));


        //populate appointment table with all appointments, if table is not yet populated

       /* allTimeRadio.setSelected(true);

        apptTable.setItems(AppointmentDao.getAllAppointments());

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDTCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDTCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdTopCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
*/


    }
}
