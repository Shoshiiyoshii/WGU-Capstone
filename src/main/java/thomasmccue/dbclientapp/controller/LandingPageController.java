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
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;

/**
 * This class manages the main landing page for the GUI, landingPage.fxml
 */
public class LandingPageController implements Initializable {
    @FXML
    private Label ynUpcomingAppointment, nextAppts, title, customersTitle, customerErrorMessage, apptErrorMessage,
            apptSearchLabel, customerOutreachLabel;
    @FXML
    private TextField apptSearchBar;
    @FXML
    public TableView<Customer> custTable;
    @FXML
    private TableColumn<Customer, Integer> custIdBottomCol, divisionIdCol;
    @FXML
    private TableColumn<Customer, String> nameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol,
            lastUpdated, lastUpdatedByCol, countryCol, statusCol;
    @FXML
    private Button custAddButton, custUpdateButton, custDeleteButton, apptAddButton1, apptUpdateButton, apptDeleteButton,
            exitButton, apptReportButton, contactSchedulesButton, customerStatusReportButton, customerLocationReportButton;
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

    /**
     * When the add button under the customer table is clicked, this method launches
     * the addOrUpdateCustomer fxml file and controller in add mode, setting the
     * title and save button text appropriately.
     *
     * @param event
     * @throws IOException
     */
    public void clickCustAdd(ActionEvent event) throws IOException {
        // Close the current window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Open the new window
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrUpdateCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AddOrUpdateCustomerController controller = fxmlLoader.getController();
        controller.setUpAdd("Add A Customer To The System", "Save");
        stage.setTitle("Add A Customer To The System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When the update button under the customer table is clicked, this method
     * checks that a customer has been selected, and displays an error message if
     * not. If a customer has been selected this method launches the
     * addOrUpdateCustomer fxml file and controller in update mode, setting the
     * title and save button text appropriately and passes the selected customer.
     *
     * @param event
     * @throws IOException
     */
    public void clickCustUpdate(ActionEvent event) throws IOException {
        SelectionModel<Customer> selectionModel = custTable.getSelectionModel();
        Customer selectedCust = selectionModel.getSelectedItem();

        if (selectedCust == null) {
            customerErrorMessage.setText("Please first select a Customer to Modify.");
        } else {
            customerErrorMessage.setText("");

            // Close the current window
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Open the new window
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


    /**
     * When the delete button under the customer table is clicked, this method
     * checks that a customer has been selected, and displays an error message if
     * not. If a customer has been selected this method launches the
     * deleteDialog window, and passes the selected customer to the deleteDialog controller.
     *
     * @param event
     * @throws IOException
     */
    public void clickCustDelete(ActionEvent event) throws IOException {
        SelectionModel<Customer> selectionModel = custTable.getSelectionModel();
        Customer selectedCust = selectionModel.getSelectedItem();

        if (selectedCust == null) {
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

    /**
     * This method is called when the search bar is selected and the enter key is typed.
     * It grabs what is typed into the apptSearchBar and holds that in a String called "search".
     * If the search field is empty, the table is repopulated with all available appointments.
     * If the search field is not empty, the method checks if search input is an int or
     * a string so that the proper method in the AppointmentDAO can be called.
     * It uses regex to see if search input is digits, i.e. a number or not.
     * If no appointments containing the searched for String or ID int are found, an error message
     * is shown, and the search bar and table view are reset.
     *
     * @param event
     * @throws IOException
     */
    public void apptSearchBarClick(ActionEvent event) throws IOException {
        String search = apptSearchBar.getText();
        apptErrorMessage.setText("");

        if (search.isEmpty()) {
            apptTable.setItems(AppointmentDao.getAllAppointments());
        } else if (search.matches("\\d+")) {
            int idSearched = Integer.parseInt(search);
            ObservableList<Appointment> foundAppointments = AppointmentDao.getAppointmentById(idSearched);

            if (!foundAppointments.isEmpty()) {
                apptErrorMessage.setText("");
                apptTable.setItems(foundAppointments);
            } else {
                apptErrorMessage.setText("No appointment with ID \"" + idSearched + "\" found.");
                apptSearchBar.clear();
                apptTable.setItems(AppointmentDao.getAllAppointments());
            }
        } else {
            ObservableList<Appointment> foundAppointments = AppointmentDao.getAppointmentByName(search);
            if (!foundAppointments.isEmpty()) {
                apptTable.setItems(foundAppointments);
                apptErrorMessage.setText("");

            } else {
                apptErrorMessage.setText("No appointment containing \"" + search + "\" found.");
                apptSearchBar.clear();
                apptTable.setItems(AppointmentDao.getAllAppointments());
            }
        }
    }

    @FXML


    /**
     * This method contains two different Lambda expressions.
     *
     * The method itself controls the radio buttons used to filter the appointments
     * table on the landing page by week, month, or all.
     *
     * The first Lambda expression is used to concisely return a list of appointments
     * that has been filtered from the displayAppts list, which contains every appointment.
     * This lambda returns a list only containing appointments that are happening in the current month.
     *
     * The second Lambda expression is used to concisely return a list of appointments
     * that has been filtered from the displayAppts list, which contains every appointment.
     * This lambda returns a list only containing appointments that are happening in the current week.
     *
     * @param event
     */
    public void radioFilter(ActionEvent event) {
        //get the current date to compare
        LocalDate now = LocalDate.now();
        if (monthRadio.isSelected()) {
            //get the current month and year
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            //Lambda to create a filtered list of appointments that occur during the current month and year
            FilteredList<Appointment> thisMonthsAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
                LocalDateTime start = appointment.getStart();
                return start.getMonthValue() == currentMonth && start.getYear() == currentYear;
            });

            // set the filtered list to the TableView
            apptTable.setItems(thisMonthsAppts);

        } else if (weekRadio.isSelected()) {
            //get current week range, dependant on location
            TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
            //start of week
            LocalDate startOfWeek = now.with(fieldISO, 1);
            //end of week
            LocalDate endOfWeek = now.with(fieldISO, 7);

            //Lambda to create a filtered list of appointments that occur during the current month and year
            FilteredList<Appointment> thisWeeksAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
                LocalDateTime start = appointment.getStart();
                return !start.toLocalDate().isBefore(startOfWeek) && !start.toLocalDate().isAfter(endOfWeek);
            });

            apptTable.setItems(thisWeeksAppts);
        } else if (allTimeRadio.isSelected()) {
            apptTable.setItems(AppointmentDao.displayAppt);
        }
    }

    /**
     * When the add button under the appointments table is clicked, this method launches
     * the addOrModifyAppointmentPage fxml file and controller in add mode, setting the
     * title and save button text appropriately.
     *
     * @param event
     * @throws IOException
     */
    public void clickApptAdd(ActionEvent event) throws IOException {
        // Close the current window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Open the new window
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrModifyAppointmentPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AddOrUpdateAppointmentController controller = fxmlLoader.getController();
        controller.setUpAdd("Create a new Appointment", "Save");
        stage.setTitle("Create A New Appointment");
        controller.setLandingPageController(this);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * When the update button under the appointment table is clicked, this method
     * checks that an appointment has been selected, and displays an error message if
     * not. If an appointment has been selected this method launches the
     * addOrModifyAppointmentPage fxml file and controller in update mode, setting the
     * title and save button text appropriately, and passing the selected appointment.
     *
     * @param event
     * @throws IOException
     */
    public void clickApptUpdate(ActionEvent event) throws IOException {
        SelectionModel<Appointment> selectionModel = apptTable.getSelectionModel();
        Appointment selectedAppt = selectionModel.getSelectedItem();

        if (selectedAppt == null) {
            apptErrorMessage.setText("Please select an existing appointment to modify.");
        } else {
            apptErrorMessage.setText("");

            // Close the current window
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Open the new window
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addOrModifyAppointmentPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AddOrUpdateAppointmentController controller = fxmlLoader.getController();
            controller.setLandingPageController(this);
            controller.setUpModify("Update An Existing Appointment", "Save Changes", selectedAppt);

            stage.setTitle("Update Existing Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * When the delete button under the appointments table is clicked, this method
     * checks that an appointment has been selected, and displays an error message if
     * not. If an appointment has been selected this method launches the
     * deleteDialog window, and passes the selected appointment to the deleteDialog controller.
     *
     * @param event
     * @throws IOException
     */
    public void clickApptDelete(ActionEvent event) throws IOException {
        SelectionModel<Appointment> selectionModel = apptTable.getSelectionModel();
        Appointment selectedAppt = selectionModel.getSelectedItem();

        if (selectedAppt == null) {
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

    /**
     * The upcomingAppts method manages the alert at the top of the landing page, which displays
     * how many appointments are scheduled to start in the next 15 minutes. If there are appointments
     * scheduled to start within 15 minutes of log in, the appointment ID and start time is displayed.
     */
    public void upcomingAppts() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in15Mins = now.plusMinutes(15);

        FilteredList<Appointment> soonAppts = new FilteredList<>(AppointmentDao.displayAppt, appointment -> {
            LocalDateTime start = appointment.getStart();
            return ((start.isEqual(now) || start.isAfter(now))
                    && (start.isBefore(in15Mins) || start.isEqual(in15Mins)));
        });
        if (!soonAppts.isEmpty()) {
            ynUpcomingAppointment.setText("You have " + soonAppts.size() + " appointments" +
                    " starting in the next 15 minutes.");
            StringBuilder stringBuilder = new StringBuilder();

            for (Appointment appointment : soonAppts) {
                String id = String.valueOf(appointment.getApptId());
                stringBuilder.append("Appointment ID: " + id);
                String time = appointment.getStart().format(formatter);
                stringBuilder.append(" Starts at: " + time + ", ");
            }
            String upcoming = stringBuilder.toString();
            nextAppts.setText(upcoming.substring(0, upcoming.length() - 2));
        } else {
            ynUpcomingAppointment.setText("You have no appointments starting in the next 15 minutes.");
            nextAppts.setText("");
        }
    }

    /**
     * This method launches the appointment report window when the View Appointment Reports button
     * is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void apptReportButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appointmentReport.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        AppointmentReportController controller = fxmlLoader.getController();
        stage.setTitle("Appointment Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method launches the contact schedules report window when the View Contact Schedules
     * button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void contactSchedulesClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("contactSchedule.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        ContactScheduleController controller = fxmlLoader.getController();
        stage.setTitle("Contact Schedules");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method launches the customer location report window when the View Customer Location Distribution
     * button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void customerLocationReportButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("customerLocationReport.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        CustomerLocationReportController controller = fxmlLoader.getController();
        stage.setTitle("Customer Location Distribution");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method launches the customer status report window when the View Customer Status Report
     * button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void customerStatusReportButtonClicked(ActionEvent event) throws IOException {

    }

    /**
     * This method closes the window when the exit button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void exitClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The initialize method is called first when the landing page window is opened.
     * It ensures that the customer and appointment tables are populated, and calls the
     * method to set the upcoming appointments notification, set the table columns, and refresh the table data.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> AllCustomers = CustomerDao.getAllCust();
        custTable.setItems(AllCustomers);

        custIdBottomCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
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

        SetOutreachNotification(AllCustomers);
        upcomingAppts();
    }

    /**
     * This method is called when the customer table is populated in order to set the outreach notification prompt.
     * The method sorts the ObservableList AllCustomers which is passed to it, and counts how many instances of each
     * subclass of Customer exist in the list. Then it dynamically sets the outreach notification to reflect the
     * current number of Inactive and New customers.
     *
     * @param AllCustomers
     */
    public void SetOutreachNotification(ObservableList<Customer> AllCustomers) {
        int inactiveCust = 0;
        int newCust = 0;
        for (Customer customer : AllCustomers) {
            if (customer instanceof NewCustomer) {
                newCust++;  // Increment for NewCustomer
            } else if (customer instanceof InactiveCustomer) {
                inactiveCust++;  // Increment for InactiveCustomer
            }
        }
        customerOutreachLabel.setText("There are " + inactiveCust + " Inactive Customers, and " + newCust +
                " New Customers. Make appointments with these customers ASAP to maintain healthy business relationships.");
    }
}
