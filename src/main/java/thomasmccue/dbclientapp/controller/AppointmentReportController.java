package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import thomasmccue.dbclientapp.dao.AppointmentDao;
import thomasmccue.dbclientapp.model.Appointment;
import thomasmccue.dbclientapp.model.ApptMonth;
import thomasmccue.dbclientapp.model.TypeAppt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;


public class AppointmentReportController implements Initializable {
    @FXML
    private TableView<ApptMonth> monthTable;
    @FXML
    private TableView<TypeAppt> typeTable;
    @FXML
    private TableColumn<ApptMonth, String> monthColumn;
    @FXML
    private TableColumn<ApptMonth, Integer> apptsColumn1;
    @FXML
    private TableColumn<TypeAppt, String> typeColumn;
    @FXML
    private TableColumn<TypeAppt, Integer>apptsColumn2;
    @FXML
    private Button exitButton;

    private ObservableList<ApptMonth> monthAppointments = FXCollections.observableArrayList();
    private ObservableList<TypeAppt> typeAppointments = FXCollections.observableArrayList();

    public void populateMonthAppointments(){
        String[] months = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        for (String month: months) {
            int appts = 0;
            for (Appointment appointment :AppointmentDao.displayAppt) {
                LocalDateTime start = appointment.getStart();
                String apptInMonth = start.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                if(apptInMonth.matches(month)){
                    appts++;
                }
            }
            ApptMonth apptMonth = new ApptMonth(month, appts);
            monthAppointments.add(apptMonth);
        }
        monthTable.setItems(monthAppointments);

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        apptsColumn1.setCellValueFactory(new PropertyValueFactory<>("appt"));
    }

    public void populateTypeAppointments(){
        //using hash set since there is no set list of types of appointments,
        // this will get types in the system without duplicates
       HashSet<String> uniqueTypes = new HashSet<>();
        //populate hash set with all types in the appointment table
        for (Appointment appointment: AppointmentDao.displayAppt) {
            String type = appointment.getType();
            uniqueTypes.add(type);
        }

        //go through all unique types, starting with 0 appts per type
        for (String type : uniqueTypes) {
            int appts = 0;
            //go through appointments, if appointment is of the type, increment appts variable
            for (Appointment appointment : AppointmentDao.displayAppt) {
                String apptType = appointment.getType();
                    if (type.matches(apptType)) {
                        appts++;
                    }
                }
            //create object with the type and number of appts of that type
            //add to observable list for display
            TypeAppt typeAppt = new TypeAppt(type, appts);
            typeAppointments.add(typeAppt);
            }
        //set table
        typeTable.setItems(typeAppointments);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptsColumn2.setCellValueFactory(new PropertyValueFactory<>("appts"));
    }

    public void exitClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateMonthAppointments();
        populateTypeAppointments();

    }
}
