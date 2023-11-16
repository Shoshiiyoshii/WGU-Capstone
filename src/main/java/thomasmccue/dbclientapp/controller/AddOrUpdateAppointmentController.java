package thomasmccue.dbclientapp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.dao.ContactDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.UserDao;
import thomasmccue.dbclientapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


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

        public void selectOrEnterContact(ActionEvent event)throws IOException {

        }

        public void selectOrEnterCust(ActionEvent event)throws IOException{

        }
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

                                String contactValue = selectContactBox.getValue();
                                //split the string at the comma
                                String[] parts = contactValue.split(",");
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
                                                Stage stage = (Stage) saveButton.getScene().getWindow();
                                                stage.close();
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

                                String contactValue = selectContactBox.getValue();
                                //split the string at the comma
                                String[] parts = contactValue.split(",");
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
                                                        Stage stage = (Stage) saveButton.getScene().getWindow();
                                                        stage.close();
                                                } else {
                                                        errorMessage.setText("There was a problem updating the appointment" +
                                                                " in the database.");
                                                }
                                        }
                                }
                        }
                }
        }

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
        public static boolean apptTimesStartBeforeEnd (ZonedDateTime start, ZonedDateTime end){
                ZoneId et = ZoneId.of("America/New_York");

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();

                ZonedDateTime etStart = ZonedDateTime.ofInstant(startInstant, et);
                ZonedDateTime etEnd = ZonedDateTime.ofInstant(endInstant, et);

                boolean isStartTimeBeforeEndTime = etStart.isBefore(etEnd);

                return isStartTimeBeforeEndTime;
        }

        //user input is in local time, and start and end times in the displayAppt
        // list are also in local time, so can be compared directly.
        public static boolean apptOverlaps (ZonedDateTime start, ZonedDateTime end, int custId, int apptId) {
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


        public void cancelClicked(ActionEvent event)throws IOException {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        public void setUpAdd(String titleText, String buttonText) throws IOException{
                pageTitleLabel.setText(titleText);
                saveButton.setText(buttonText);
                apptIdField.setText("Appointment ID will be auto-assigned on save");
        }
        //setup, different depending on whether modify or add button are clicked
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

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCustBox.setItems(CustomerDao.getAllCustId());
        selectContactBox.setItems(ContactDao.getListOfContacts());
        selectUserIdBox.setItems(UserDao.getAllUserId());
        }
}
