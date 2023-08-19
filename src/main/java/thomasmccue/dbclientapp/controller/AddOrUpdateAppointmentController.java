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
import thomasmccue.dbclientapp.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static thomasmccue.dbclientapp.helper.JDBC.connection;

public class AddOrUpdateAppointmentController implements Initializable {
        @FXML
        private Label appointmentIdLabel, selectContactLabel, titleLabel, descLabel, locationLabel, typeLabel, startDTLabel;
        @FXML
        private Label endDTLabel, errorMessage, selectCustIdLabel, userIdLabel, pageTitleLabel;
        @FXML
        private TextField apptIdField, titleField, descField, locationField, typeField, startDTField, endDTField, userIdField;
        @FXML
        private ComboBox<String> selectCustBox, selectContactBox;
        @FXML
        private Button saveButton, cancelButton;

        private Appointment appointment;

        public void selectOrEnterContact(ActionEvent event)throws IOException {

        }

        public void selectOrEnterCust(ActionEvent event)throws IOException{

        }
        public void saveClicked(ActionEvent event)throws IOException{
                if(saveButton.getText().equals("Save Changes")){
                        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Appointments WHERE Appointment_ID = ?")) {
                                preparedStatement.setString(1, apptIdField.getText());

                                ResultSet resultSet = preparedStatement.executeQuery();
                                if(resultSet.next()){
                                        Appointment oldAppointment = new Appointment();
                                        oldAppointment.setApptId(resultSet.getInt("Appointment_ID"));
                                        oldAppointment.setTitle(resultSet.getString("Title"));
                                        oldAppointment.setDesc(resultSet.getString("Description"));
                                        oldAppointment.setLocation(resultSet.getString("Location"));
                                        oldAppointment.setType(resultSet.getString("Type"));
                                        oldAppointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
                                        oldAppointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
                                        oldAppointment.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                                        oldAppointment.setCreatedBy(resultSet.getString("Created_By"));
                                        oldAppointment.setLastUpdate(resultSet.getObject("Last_Update", LocalDateTime.class));
                                        oldAppointment.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                                        oldAppointment.setCustId(resultSet.getInt("Customer_ID"));
                                        oldAppointment.setUserId(resultSet.getInt("User_ID"));
                                        oldAppointment.setContactId(resultSet.getInt("Contact_ID"));

                                        Appointment newAppointment = new Appointment();
                                        newAppointment.setTitle(titleField.getText());
                                        newAppointment.setDesc(descField.getText());
                                        newAppointment.setLocation(locationField.getText());
                                        newAppointment.setType(typeField.getText());
                                        newAppointment.setStart(Timestamp.valueOf(startDTField.getText()).toLocalDateTime());
                                        newAppointment.setEnd(Timestamp.valueOf(endDTField.getText()).toLocalDateTime());
                                        newAppointment.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                                        newAppointment.setCreatedBy(resultSet.getString("Created_By"));
                                        newAppointment.setLastUpdate(LocalDateTime.now());
                                        newAppointment.setLastUpdatedBy(String.valueOf(LogInController.getLoggedInUserId()));
                                        newAppointment.setCustId(Integer.parseInt(selectCustBox.getValue()));
                                        newAppointment.setUserId(Integer.parseInt(userIdField.getText()));
                                        newAppointment.setContactId(Integer.parseInt(selectContactBox.getValue()));

                                        AppointmentDao.editAppt(oldAppointment, newAppointment);
                                        Stage stage = (Stage) saveButton.getScene().getWindow();
                                        stage.close();
                                }
                } catch (SQLException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                        }
                }else{

                        String title = titleField.getText();
                        String desc = descField.getText();
                        String location = locationField.getText();
                        String type = typeField.getText();
                        LocalDateTime start = (Timestamp.valueOf(startDTField.getText()).toLocalDateTime());
                        LocalDateTime end = (Timestamp.valueOf(endDTField.getText()).toLocalDateTime());
                        int createdBy = (LogInController.getLoggedInUserId());
                        int customer = (Integer.parseInt(selectCustBox.getValue()));
                        String user = (userIdField.getText());
                        int userId =  Integer.parseInt(user);
                        int contact = (Integer.parseInt(selectContactBox.getValue()));

                        Appointment newAppointment = new Appointment(title,desc,location,type,start,end,user,LocalDateTime.now(),user,customer,userId,contact);

                        AppointmentDao.addAppt(newAppointment);
                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                }
        }
        public void cancelClicked(ActionEvent event)throws IOException {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        public void setUpAdd(String titleText, String buttonText) throws IOException{
                pageTitleLabel.setText(titleText);
                saveButton.setText(buttonText);
                apptIdField.setVisible(false);
                appointmentIdLabel.setVisible(false);
        }
        //setup, different depending on whether modify or add button are clicked
        public void setUpModify(String titleText, String buttonText, int apptID) throws IOException{
                pageTitleLabel.setText(titleText);
                saveButton.setText(buttonText);
                apptIdField.setText(String.valueOf(apptID));
        }
        public void preFillFields(Appointment selectedAppt) throws IOException{
                this.appointment = selectedAppt;

                apptIdField.setText(String.valueOf(appointment.getApptId()));
                titleField.setText(appointment.getTitle());
                descField.setText(appointment.getDesc());
                locationField.setText(appointment.getLocation());
                typeField.setText(appointment.getType());
                startDTField.setText(String.valueOf(appointment.getStart()));
                endDTField.setText(String.valueOf(appointment.getEnd()));
                selectContactBox.getSelectionModel().select(String.valueOf(appointment.getContactId()));
                selectCustBox.getSelectionModel().select(String.valueOf(appointment.getCustId()));
                userIdField.setText(String.valueOf(appointment.getUserId()));
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
