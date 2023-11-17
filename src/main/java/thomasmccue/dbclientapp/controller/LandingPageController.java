package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.sql.SQLException;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;

public class LandingPageController implements Initializable {
    @FXML
    private Label ynUpcomingAppointment, nextAppts, title, customersTitle, customerErrorMessage, apptErrorMessage;
    @FXML
    public TableView<Customer> custTable;
    @FXML
    private TableColumn<Customer, Integer> custIdBottomCol, divisionIdCol;
    @FXML
    private TableColumn<Customer, String> nameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdated, lastUpdatedByCol, countryCol;
    @FXML
    private Button custAddButton, custUpdateButton, custDeleteButton, apptAddButton1, apptUpdateButton, apptDeleteButton,exitButton, apptReportButton, contactSchedulesButton, customerReportButton;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol, contactIdCol, custIdTopCol, userIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol, descCol, locationCol, typeCol, startDTCol, endDTCol;
    @FXML
    private RadioButton weekRadio, monthRadio, allTimeRadio;
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

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
    public void clickCustDelete(ActionEvent event) throws IOException{
        SelectionModel<Customer> selectionModel = custTable.getSelectionModel();
        Customer selectedCust = selectionModel.getSelectedItem();

        if(selectedCust == null) {
            customerErrorMessage.setText("Please first select a Customer to Delete.");
        } else {
            customerErrorMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("deleteDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            DeleteDialogController controller = fxmlLoader.getController();
            controller.setUpCustomerDelete(selectedCust);
            stage.setTitle("Delete Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void radioFilter(ActionEvent event){
        //get the current date to compare
        LocalDate now = LocalDate.now();
        if(monthRadio.isSelected()){
            //get the current month and year
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            //FIXME LAMBDA HERE
            //create a filtered list of appointments that occur during the current month and year
            FilteredList<Appointment> thisMonthsAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
                LocalDateTime start = appointment.getStart();
                return start.getMonthValue() == currentMonth && start.getYear() == currentYear;
            });

            // set the filtered list to the TableView
            apptTable.setItems(thisMonthsAppts);

        } else if (weekRadio.isSelected()) {
            //get current week range
            TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
            LocalDate startOfWeek = now.with(fieldISO, 1); // Start of week (e.g., Monday)
            LocalDate endOfWeek = now.with(fieldISO, 7); // End of week (e.g., Sunday)

            //FIXME LAMBDA HERE
            FilteredList<Appointment> thisWeeksAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
                LocalDateTime start = appointment.getStart();
                return !start.toLocalDate().isBefore(startOfWeek) && !start.toLocalDate().isAfter(endOfWeek);
            });

            apptTable.setItems(thisWeeksAppts);
        }else if(allTimeRadio.isSelected()){
           apptTable.setItems(AppointmentDao.displayAppt);
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
            controller.setUpModify("Update An Existing Appointment", "Save Changes", selectedAppt);

            stage.setTitle("Update Existing Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void clickApptDelete(ActionEvent event) throws SQLException, IOException {
        SelectionModel<Appointment> selectionModel = apptTable.getSelectionModel();
        Appointment selectedAppt = selectionModel.getSelectedItem();

        if(selectedAppt == null) {
            customerErrorMessage.setText("Please first select an Appointment to Delete.");
        } else {
            customerErrorMessage.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("deleteDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            DeleteDialogController controller = fxmlLoader.getController();
            controller.setUpAppointmentDelete(selectedAppt);
            stage.setTitle("Delete Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void upcomingAppts(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in15Mins = now.plusMinutes(15);

        FilteredList<Appointment> soonAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
            LocalDateTime start = appointment.getStart();
            return ((start.isEqual(now) || start.isAfter(now))
                    && (start.isBefore(in15Mins) || start.isEqual(in15Mins)));
        });
            if(!soonAppts.isEmpty()){
                ynUpcomingAppointment.setText("You have " + soonAppts.size() +" appointments" +
                        " starting in the next 15 minutes.");
                StringBuilder stringBuilder = new StringBuilder();

                for (Appointment appointment: soonAppts) {
                    String id = String.valueOf(appointment.getApptId());
                    stringBuilder.append("Appointment ID: " + id);
                    String time = appointment.getStart().format(formatter);
                    stringBuilder.append(" Starts at: "+ time + ", ");
                }
                String upcoming = stringBuilder.toString();
                nextAppts.setText(upcoming.substring(0, upcoming.length() - 2));
            } else {
                ynUpcomingAppointment.setText("You have no appointments starting in the next 15 minutes.");
                nextAppts.setText("");
            }
    }

    public void apptReportButtonClicked(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appointmentReport.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AppointmentReportController controller = fxmlLoader.getController();
        stage.setTitle("Appointment Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void contactSchedulesClicked(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("contactSchedule.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

       ContactScheduleController controller = fxmlLoader.getController();
        stage.setTitle("Contact Schedules");
        stage.setScene(scene);
        stage.show();
    }

    public void customerReportButtonClicked(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerReport.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        CustomerReportController controller = fxmlLoader.getController();
        stage.setTitle("Customer Location Distribution");
        stage.setScene(scene);
        stage.show();
    }

    public void exitClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
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

        allTimeRadio.setSelected(true);

        upcomingAppts();
    }
}
