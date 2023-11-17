package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.ContactAppts;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class managed the CRUD to the mysql database for Contact objects.
 */
public class ContactDao {

    /**
     * Querys the client_schedule.contacts table to get a column of
     * strings containing all of the contact IDs and contact names,
     * the result is a string in the format "Contact_ID, Contact_Name".
     * Used to display contacts so that both ID and Name can be used to
     * select a contact easily.
     *
     * @return ObservableList of strings in the format "Contact_ID, Contact_Name"
     */
    public static ObservableList<String> getListOfContacts() {
        String sql = "SELECT CONCAT_WS(', ', Contact_ID, Contact_Name)" +
                " FROM client_schedule.contacts;";
        ObservableList<String> allContacts = FXCollections.observableArrayList();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allContacts.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allContacts;
    }

    /**
     * This method queries the client_schedule.appointments for appointments
     * belonging to a specific contact. This is used to populate the contact schedule report.
     * Times are explictly converted to local/systemDefault from UTC for display.
     *
     * @param contactId ID of contact whose appointments are needed
     * @return ObservableList of ContactAppts objects
     */
    public static ObservableList<ContactAppts> getContactAppts(int contactId) {
        String sql = "SELECT  Appointment_ID, Title, Description, Type, Start, End, Customer_ID" +
                " FROM client_schedule.appointments WHERE Contact_ID = ?";
        ObservableList<ContactAppts> contactAppts = FXCollections.observableArrayList();

        ZoneId localZone = ZoneId.systemDefault();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ZonedDateTime utcStartTime = resultSet.getObject("Start", ZonedDateTime.class);
                ZonedDateTime localStartTime = utcStartTime.withZoneSameInstant(localZone);
                LocalDateTime displayStartTime = localStartTime.toLocalDateTime();

                ZonedDateTime utcEndTime = resultSet.getObject("End", ZonedDateTime.class);
                ZonedDateTime localEndTime = utcEndTime.withZoneSameInstant(localZone);
                LocalDateTime displayEndTime = localEndTime.toLocalDateTime();

                ContactAppts contactAppt = new ContactAppts(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Type"),
                        displayStartTime,
                        displayEndTime,
                        resultSet.getInt("Customer_ID")
                );

                contactAppts.add(contactAppt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return contactAppts;
    }
}
