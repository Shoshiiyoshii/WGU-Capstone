package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.Main;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.dao.ContactDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.UserDao;
import thomasmccue.dbclientapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * This class serves as a controller and manages
 * the addOrModifyAppointmentPage.fxml. Depending on
 * whether the Add or Update buttons were used to access
 * it from the landing page, it behaves differently.
 */
public class AddOrUpdateAppointmentController implements Initializable {
        @FXML
        private Label appointmentIdLabel, selectContactLabel, titleLabel, descLabel, locationLabel, typeLabel, startDTLabel;
        @FXML
        private Label endDTLabel, errorMessage, selectCustIdLabel, userIdLabel, pageTitleLabel;
        @FXML
        private TextField apptIdField, titleField, descField, locationField, typeField, startDTField, endDTField;
        @FXML
        private ComboBox<String> selectCustBox, selectContactBox, selectUserIdBox;
        @FXML
        private Button saveButton, cancelButton;

        //assumes times are input in users local time
        final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")
                .withZone(ZoneId.systemDefault());
        private Appointment appointment;

        private LandingPageController landingPageController;

        public void setLandingPageController(LandingPageController controller) {
                this.landingPageController = controller;
        }

        public void selectOrEnterContact(ActionEvent event)throws IOException {

        }

        public void selectOrEnterCust(ActionEvent event)throws IOException{

        }
        /**
         * This method is called when the save button is clicked on the add appointment or the
         * update appointment forms.
         *
         * If the controller is accessed from the Add button, the save button will say "Save".
         * In this case user input is collected from the form. Checks are executed to ensure
         * the form is filled out, and to ensure user input for appointment times are valid.
         * If the checks are all passed, an Appointment object is created from the users input
         * and sent to the AppointmentDao.addAppt() method to be added to the sql table and in
         * the GUI for the landing page. If the checks are not passed, an appropriate error
         * message is printed to the GUI.
         *
         * If the controller is accessed from the Update button, the save button will say "Save Changes".
         * In this case user input is checked to ensure the form is filled out, and to ensure user input
         * for appointment times are valid. If the user input passes the checks the mutators/setters for
         * the selected appointment object are called, and update the appointment object so that the users
         * input is set. Then the appointment is sent to AppointmentDao.updateAppt() method to be updated
         * in the sql table and in the GUI for the landing page. If the checks are not passed, an
         * appropriate error message is printed to the GUI.
         *
         * @param event
         * @throws IOException
         */
        public void saveClicked(ActionEvent event)throws IOException {
                if (saveButton.getText().equals("Save")) {
                        String title = titleField.getText();
                        String desc = descField.getText();
                        String location = locationField.getText();
                        String type = typeField.getText();

                        String start = startDTField.getText();
                        String end = endDTField.getText();

                        String createdBy = (LogInController.loggedInUser);

                        //check to make sure form is complete
                        if (title.isEmpty() || desc.isEmpty() || location.isEmpty() ||
                                type.isEmpty() || selectUserIdBox.getValue().isEmpty() ||
                                selectContactBox.getValue().isEmpty() || selectCustBox.getValue().isEmpty()) {
                                errorMessage.setText("Please first ensure all fields are filled out.");
                        } else {
                                int custId = Integer.parseInt(selectCustBox.getValue());
                                int userId = Integer.parseInt(selectUserIdBox.getValue());

                                String contact = selectContactBox.getValue();
                                //split the string at the comma
                                String[] parts = contact.split(",");
                                //parse the first part (after trimming spaces) to an integer
                                int contactId = Integer.parseInt(parts[0].trim());

                                if (start.isEmpty() || end.isEmpty()) {
                                        errorMessage.setText("Please input valid appointment start and end times\n" +
                                                "in the format mm-dd-yyyy hh:mm");
                                } else {
                                        ZonedDateTime apptStart = ZonedDateTime.parse(start, formatter);
                                        ZonedDateTime apptEnd = ZonedDateTime.parse(end, formatter);

                                        if (!apptTimesInBusinessHours(apptStart, apptEnd)) {
                                                errorMessage.setText("Please ensure the appointment is scheduled\n" +
                                                        "within the business hours of 8:00am ET to 10:00pm ET");
                                        } else if (!apptTimesStartBeforeEnd(apptStart, apptEnd)) {
                                                errorMessage.setText("Please ensure the appointment start time is\n" +
                                                        "before the appointment end time");
                                        } else if (apptOverlaps(apptStart, apptEnd, custId, -1)) {
                                                errorMessage.setText("Please ensure that the chosen start and end times do\n" +
                                                        "not overlap with an already existing appointment.");
                                        } else {
                                                LocalDateTime localStart = (apptStart.toLocalDateTime());
                                                LocalDateTime localEnd = (apptEnd.toLocalDateTime());


                                                Appointment newAppointment = new Appointment(
                                                        title,
                                                        desc,
                                                        location,
                                                        type,
                                                        localStart,
                                                        localEnd,
                                                        null,
                                                        createdBy,
                                                        null,
                                                        null,
                                                        custId,
                                                        userId,
                                                        contactId
                                                );
                                                AppointmentDao.addAppt(newAppointment);
                                                Stage currentStage = (Stage) (saveButton.getScene().getWindow());
                                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
                                                Scene scene = new Scene(fxmlLoader.load());
                                                currentStage.setScene(scene);
                                                currentStage.setTitle("Customer and Appointment Management Portal");
                                                currentStage.centerOnScreen();
                                                currentStage.show();
                                        }
                                }
                        }
                        //update appointment
                } else {
                        int index = AppointmentDao.displayAppt.indexOf(appointment);

                        String title = titleField.getText();
                        String desc = descField.getText();
                        String location = locationField.getText();
                        String type = typeField.getText();

                        String start = startDTField.getText();
                        String end = endDTField.getText();

                        String updatedBy = (LogInController.loggedInUser);

                        //check to make sure form is complete
                        if (title.isEmpty() || desc.isEmpty() || location.isEmpty() ||
                                type.isEmpty() || selectUserIdBox.getValue().isEmpty() ||
                                selectContactBox.getValue().isEmpty() || selectCustBox.getValue().isEmpty()) {

                                errorMessage.setText("Please first ensure all fields are filled out.");
                        } else {
                                int custId = Integer.parseInt(selectCustBox.getValue());
                                int userId = Integer.parseInt(selectUserIdBox.getValue());

                                String contact = selectContactBox.getValue();
                                //split the string at the comma
                                String[] parts = contact.split(",");
                                //parse the first part (after trimming spaces) to an integer
                                int contactId = Integer.parseInt(parts[0].trim());

                                if (start.isEmpty() || end.isEmpty()) {
                                        errorMessage.setText("Please input valid appointment start and end times\n" +
                                                "in the format yyyy-mm-dd hh:mm:ss");
                                } else {
                                        ZonedDateTime apptStart = ZonedDateTime.parse(start, formatter);
                                        ZonedDateTime apptEnd = ZonedDateTime.parse(end, formatter);

                                        if (!apptTimesInBusinessHours(apptStart, apptEnd)) {
                                                errorMessage.setText("Please ensure the appointment is scheduled\n" +
                                                        "within the business hours of 8:00am ET to 10:00pm ET");
                                        } else if (!apptTimesStartBeforeEnd(apptStart, apptEnd)) {
                                                errorMessage.setText("Please ensure the appointment start time is\n" +
                                                        "before the appointment end time");
                                        } else if (apptOverlaps(apptStart, apptEnd, custId, appointment.getApptId())) {
                                                errorMessage.setText("Please ensure that the chosen start and end times do\n" +
                                                        "not overlap with an already existing appointment.");
                                        } else {
                                                LocalDateTime localStart = (apptStart.toLocalDateTime());
                                                LocalDateTime localEnd = (apptEnd.toLocalDateTime());

                                                appointment.setTitle(title);
                                                appointment.setDesc(desc);
                                                appointment.setLocation(location);
                                                appointment.setType(type);
                                                appointment.setStart(localStart);
                                                appointment.setEnd(localEnd);
                                                appointment.setLastUpdatedBy(updatedBy);
                                                appointment.setCustId(custId);
                                                appointment.setContactId(contactId);
                                                appointment.setUserId(userId);

                                                boolean updated = AppointmentDao.updateAppt(appointment, index);
                                                if (updated) {
                                                        Stage currentStage = (Stage) (saveButton.getScene().getWindow());
                                                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
                                                        Scene scene = new Scene(fxmlLoader.load());
                                                        currentStage.setScene(scene);
                                                        currentStage.setTitle("Customer and Appointment Management Portal");
                                                        currentStage.centerOnScreen();
                                                        currentStage.show();
                                                } else {
                                                        errorMessage.setText("There was a problem updating the appointment" +
                                                                " in the database.");
                                                }
                                        }
                                }
                        }
                }
        }

        /**
         * This method is called by the saveClicked method. It checks that the user input for
         * an appointment start and end time are within the business hours of 8:00am to 10:00pm et.
         *
         * @param start the users chosen start date/time
         * @param end the users chosen end date/time
         * @return boolean to show whether start and end are within business hours.
         */
        public static boolean apptTimesInBusinessHours (ZonedDateTime start, ZonedDateTime end){
                ZoneId et = ZoneId.of("America/New_York");

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();

                ZonedDateTime etStart = ZonedDateTime.ofInstant(startInstant, et);
                ZonedDateTime etEnd = ZonedDateTime.ofInstant(endInstant, et);

                ZonedDateTime businessOpen = etStart.with(LocalTime.of(8, 0));
                ZonedDateTime businessClosed = etStart.with(LocalTime.of(22, 0));

                boolean isWithinBusinessHours = !etStart.isBefore(businessOpen) && !etStart.isAfter(businessClosed)
                        && !etEnd.isBefore(businessOpen) && !etEnd.isAfter(businessClosed);

                return isWithinBusinessHours;
        }
        /**
         * This method is called by the saveClicked method. It checks that the user input for
         * an appointment start time is before the user input for an appointment end time.
         *
         * @param start the users chosen start date/time
         * @param end the users chosen end date/time
         * @return boolean to show whether start is before end
         */
        public static boolean apptTimesStartBeforeEnd (ZonedDateTime start, ZonedDateTime end){
                ZoneId et = ZoneId.of("America/New_York");

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();

                ZonedDateTime etStart = ZonedDateTime.ofInstant(startInstant, et);
                ZonedDateTime etEnd = ZonedDateTime.ofInstant(endInstant, et);

                boolean isStartTimeBeforeEndTime = etStart.isBefore(etEnd);

                return isStartTimeBeforeEndTime;
        }
        /**
         * This method is called by the saveClicked method. It checks that the user input for
         * appointment start and end times do not overlap with an already scheduled appointment
         * for the selected customer, as a customer cannot be in two places at once.
         *
         * @param start the users chosen start date/time
         * @param end the users chosen end date/time
         * @param apptId used to ensure that in update mode, the appointment being updated doesn't
         *               cause a false scheduling conflict
         * @param custId only appointments for the same customer must be checked for overlap
         * @return boolean to show whether there is a scheduling conflict
         */

        public static boolean apptOverlaps (ZonedDateTime start, ZonedDateTime end, int custId, int apptId) {
                //user input is in local time, and start and end times in the displayAppt
                // list are also in local time, so can be compared directly.
                LocalDateTime newStart = start.toLocalDateTime();
                LocalDateTime newEnd = end.toLocalDateTime();

                boolean overlaps = false;
                for (Appointment existingAppt : AppointmentDao.displayAppt) {
                        LocalDateTime existingStart = existingAppt.getStart();
                        LocalDateTime existingEnd = existingAppt.getEnd();
                        int existingCustId = existingAppt.getCustId();
                        int existingApptId = existingAppt.getApptId();

                        //make sure the appointment times that are being edited are exempt from the checks.
                        if(apptId == existingApptId){
                                continue;
                        }

                        if(custId == existingCustId){
                                if((existingStart.isAfter(newStart) || existingStart.isEqual(newStart))
                                        && existingStart.isBefore(newEnd)){
                                        overlaps = true;
                                        break;
                                } else if (existingEnd.isAfter(newStart)
                                        && (existingEnd.isBefore(newEnd) || existingEnd.isEqual(newEnd))) {
                                        overlaps = true;
                                        break;
                                } else if ((existingStart.isBefore(newStart) || existingStart.isEqual(newStart))
                                        && (existingEnd.isAfter(newEnd) || existingEnd.isEqual(newEnd))) {
                                        overlaps = true;
                                        break;
                                }
                        }
                }
                return overlaps;
        }

        /**
         * This method closes the Update or Add window when the cancel button is clicked.
         *
         * @param event
         * @throws IOException
         */
        public void cancelClicked(ActionEvent event)throws IOException {
                Stage currentStage = (Stage) (cancelButton.getScene().getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                currentStage.setScene(scene);
                currentStage.setTitle("Customer and Appointment Management Portal");
                currentStage.centerOnScreen();
                currentStage.show();
        }

        /**
         * This method is called from the LandingPageController if the addOrModifyAppointment.fxml
         * GUI is to be accessed from the Add button.
         *
         * @param titleText sets the title of the form
         * @param buttonText sets the text on the save button to "Save"
         * @throws IOException
         */
        public void setUpAdd(String titleText, String buttonText) throws IOException{
                pageTitleLabel.setText(titleText);
                saveButton.setText(buttonText);
                apptIdField.setText("Appointment ID will be auto-assigned on save");
        }
        /**
         * This method is called from the LandingPageController if the addOrModifyAppointment.fxml
         * GUI is to be accessed from the Update button.
         *
         * @param titleText sets the title of the form
         * @param buttonText sets the text on the save button to "Save Updates"
         * @param appointment passes the selected appointment so that it can be updated
         * @throws IOException
         */
        public void setUpModify(String titleText, String buttonText, Appointment appointment) throws IOException{
                pageTitleLabel.setText(titleText);
                saveButton.setText(buttonText);

                this.appointment = appointment;

                LocalDateTime start = appointment.getStart();
                LocalDateTime end = appointment.getEnd();

                String formattedStartTime = start.format(formatter);
                String formattedEndTime = end.format(formatter);

                apptIdField.setText(String.valueOf(appointment.getApptId()));
                titleField.setText(appointment.getTitle());
                descField.setText(appointment.getDesc());
                locationField.setText(appointment.getLocation());
                typeField.setText(appointment.getType());
                startDTField.setText(formattedStartTime);
                endDTField.setText(formattedEndTime);
                selectContactBox.getSelectionModel().select(String.valueOf(appointment.getContactId()));
                selectCustBox.getSelectionModel().select(String.valueOf(appointment.getCustId()));
                selectUserIdBox.getSelectionModel().select(String.valueOf(appointment.getUserId()));
        }

        /**
         * initialize method is called first, ensures that the comboBoxes are populated
         * correctly when the form opens.
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCustBox.setItems(CustomerDao.getAllCustId());
        selectContactBox.setItems(ContactDao.getListOfContacts());
        selectUserIdBox.setItems(UserDao.getAllUserId());

        }
}
