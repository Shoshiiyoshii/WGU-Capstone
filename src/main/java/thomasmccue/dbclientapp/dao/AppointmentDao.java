package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Appointment;


import java.sql.*;
import java.time.*;

/**
 * This class manages the CRUD to the mysql database for Appointment objects.
 */
public class AppointmentDao {
    public static ObservableList<Appointment> displayAppt = FXCollections.observableArrayList();

    /**
     * This method adds appointments to the client_schedule.appointments table and to
     * the ObservableList displayAppt which is responsible for the display
     * of appointments on the landing page. Time values are explicitly set to UTC
     * for storage.
     *
     * @param appointment the appointment to be added
     * @return boolean true/false whether add was successful
     */
    public static boolean addAppt(Appointment appointment) {
        String sql = "INSERT INTO client_schedule.appointments " +
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By," +
                " Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        ZoneId userTimeZone = ZoneId.systemDefault();

        String title = appointment.getTitle();
        String description = appointment.getDesc();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String createdBy = appointment.getCreatedBy();
        int customerID = appointment.getCustId();
        int userID = appointment.getUserId();
        int contactID = appointment.getContactId();
        //get create date as this instant in UTC
        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC"));

        //explicitly convert start and end times for appointments from users local time to UTC for storage
        LocalDateTime localStartTime = appointment.getStart();
        ZonedDateTime userZoneStartTime = localStartTime.atZone(userTimeZone);
        ZonedDateTime utcStartTime = userZoneStartTime.withZoneSameInstant(ZoneOffset.UTC);

        LocalDateTime localEndTime = appointment.getEnd();
        ZonedDateTime userZoneEndTime = localEndTime.atZone(userTimeZone);
        ZonedDateTime utcEndTime = userZoneEndTime.withZoneSameInstant(ZoneOffset.UTC);

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setObject(5, utcStartTime);
                preparedStatement.setObject(6, utcEndTime);
                preparedStatement.setObject(7, createDate);
                preparedStatement.setString(8, createdBy);
                preparedStatement.setInt(9, customerID);
                preparedStatement.setInt(10, userID);
                preparedStatement.setInt(11, contactID);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    // The insertion was successful, there are generated keys
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int apptId = generatedKeys.getInt(1);
                        appointment.setApptId(apptId);
                        displayAppt.add(appointment);
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return false;
        }


    /**
     * Deletes the appointment from the client_schedule.appointments table in mySql
     * and from the ObservableList<Appointment> displayAppt.
     *
     * @param selectedAppt appointment to delete
     * @return boolean true/false whether delete was successful
     * @throws SQLException
     */

      public static boolean deleteAppt (Appointment selectedAppt) throws SQLException {
            String sql = ("DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?");
            int apptId = selectedAppt.getApptId();
                try{
                    Connection connection = JDBC.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, apptId );

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        displayAppt.remove(selectedAppt);
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return false;
    }

    /**
     * Updates the appointment with the same ID as the appointment object it's passed
     * in the client_schedule.appointments table in mySql and in the
     * ObservableList<Appointment> displayAppt. Time values are explicitly set to UTC
     * for storage.
     *
     * @param appointment appointment to be updated
     * @param index location of appointment to be updated in displayAppt list
     * @return boolean true/false update was successful
     */
       public static boolean updateAppt (Appointment appointment, int index){
        int apptId = appointment.getApptId();

        ZoneId userTimeZone = ZoneId.systemDefault();

        String title = appointment.getTitle();
        String desc = appointment.getDesc();
        String location = appointment.getLocation();
        String type = appointment.getType();

        //explicitly converts local time to utc for storage
        LocalDateTime localStartTime = appointment.getStart();
        ZonedDateTime userZoneStartTime = localStartTime.atZone(userTimeZone);
        ZonedDateTime utcStartTime = userZoneStartTime.withZoneSameInstant(ZoneOffset.UTC);

        LocalDateTime localEndTime = appointment.getEnd();
        ZonedDateTime userZoneEndTime = localEndTime.atZone(userTimeZone);
        ZonedDateTime utcEndTime = userZoneEndTime.withZoneSameInstant(ZoneOffset.UTC);

        //records the current instant of time in UTC, which is the correct time zone for my SQL data
        ZonedDateTime updateDate = ZonedDateTime.now(ZoneId.of("UTC"));

        String updatedBy = appointment.getLastUpdatedBy();
        int custId = appointment.getCustId();
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();

        String sql = ("UPDATE client_schedule.appointments SET Title = ?," +
                " Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                " Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?," +
                " User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, desc);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setObject(5, utcStartTime);
                preparedStatement.setObject(6, utcEndTime);
                preparedStatement.setObject(7, updateDate);
                preparedStatement.setString(8, updatedBy);
                preparedStatement.setInt(9, custId);
                preparedStatement.setInt(10, userId);
                preparedStatement.setInt(11, contactId);
                preparedStatement.setInt(12, apptId);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    // The insertion was successful
                    displayAppt.set(index, appointment);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return false;
        }

    /**
     * Gets a list of all appointments in the client_schedule.appointments table. Used to populate
     * the ObservableList<Appointment> displayAppt. Explicitly converts time values to the systems
     * default time zone for display purposes.
     *
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments () {
            String sql = "SELECT * FROM client_schedule.appointments";
            ZoneId localZone = ZoneId.systemDefault();

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    //convert UTC datetimes from SQL table to the user's local times for display
                    ZonedDateTime utcStartTime = resultSet.getObject("Start", ZonedDateTime.class);
                    ZonedDateTime localStartTime = utcStartTime.withZoneSameInstant(localZone);
                    LocalDateTime displayStartTime = localStartTime.toLocalDateTime();

                    ZonedDateTime utcEndTime = resultSet.getObject("End", ZonedDateTime.class);
                    ZonedDateTime localEndTime = utcEndTime.withZoneSameInstant(localZone);
                    LocalDateTime displayEndTime = localEndTime.toLocalDateTime();

                    ZonedDateTime utcCreateTime = resultSet.getObject("Create_Date", ZonedDateTime.class);
                    ZonedDateTime localCreateTime = utcCreateTime.withZoneSameInstant(localZone);
                    LocalDateTime displayCreateTime = localCreateTime.toLocalDateTime();

                    //get last update time and see if it needs to be converted
                    ZonedDateTime utcUpdateTime = resultSet.getObject("Last_Update", ZonedDateTime.class);
                    if (utcUpdateTime != null) {
                        ZonedDateTime localUpdateTime = utcUpdateTime.withZoneSameInstant(localZone);
                        LocalDateTime displayUpdateTime = localUpdateTime.toLocalDateTime();

                        Appointment appointment = new Appointment(
                                resultSet.getInt("Appointment_ID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Location"),
                                resultSet.getString("Type"),
                                displayStartTime,
                                displayEndTime,
                                displayCreateTime,
                                resultSet.getString("Created_By"),
                                displayUpdateTime,
                                resultSet.getString("Last_Updated_By"),
                                resultSet.getInt("Customer_ID"),
                                resultSet.getInt("User_ID"),
                                resultSet.getInt("Contact_ID")
                        );

                        displayAppt.add(appointment);
                        //not all Appointments have been updated, so allow for null update time
                    } else {
                        Appointment appointment = new Appointment(
                                resultSet.getInt("Appointment_ID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Location"),
                                resultSet.getString("Type"),
                                displayStartTime,
                                displayEndTime,
                                displayCreateTime,
                                resultSet.getString("Created_By"),
                                null,
                                resultSet.getString("Last_Updated_By"),
                                resultSet.getInt("Customer_ID"),
                                resultSet.getInt("User_ID"),
                                resultSet.getInt("Contact_ID")
                        );

                        displayAppt.add(appointment);
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return displayAppt;
        }

    /**
     * Gets a list of all appointments in the client_schedule.appointments table with an ID containing the searched
     * ID. Explicitly converts time values to the systems default time zone for display purposes.
     *
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAppointmentById (int apptID) {
        ObservableList<Appointment> idSearchAppts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID LIKE ?";
        ZoneId localZone = ZoneId.systemDefault();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + apptID + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //convert UTC datetimes from SQL table to the user's local times for display
                ZonedDateTime utcStartTime = resultSet.getObject("Start", ZonedDateTime.class);
                ZonedDateTime localStartTime = utcStartTime.withZoneSameInstant(localZone);
                LocalDateTime displayStartTime = localStartTime.toLocalDateTime();

                ZonedDateTime utcEndTime = resultSet.getObject("End", ZonedDateTime.class);
                ZonedDateTime localEndTime = utcEndTime.withZoneSameInstant(localZone);
                LocalDateTime displayEndTime = localEndTime.toLocalDateTime();

                ZonedDateTime utcCreateTime = resultSet.getObject("Create_Date", ZonedDateTime.class);
                ZonedDateTime localCreateTime = utcCreateTime.withZoneSameInstant(localZone);
                LocalDateTime displayCreateTime = localCreateTime.toLocalDateTime();

                //get last update time and see if it needs to be converted
                ZonedDateTime utcUpdateTime = resultSet.getObject("Last_Update", ZonedDateTime.class);
                if (utcUpdateTime != null) {
                    ZonedDateTime localUpdateTime = utcUpdateTime.withZoneSameInstant(localZone);
                    LocalDateTime displayUpdateTime = localUpdateTime.toLocalDateTime();

                    Appointment appointment = new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            displayStartTime,
                            displayEndTime,
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            displayUpdateTime,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    );

                    idSearchAppts.add(appointment);
                    //not all Appointments have been updated, so allow for null update time
                } else {
                    Appointment appointment = new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            displayStartTime,
                            displayEndTime,
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            null,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    );

                    idSearchAppts.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return idSearchAppts;
    }

    /**
     * Gets a list of all appointments in the client_schedule.appointments table with a title containing the searched
     * string. Explicitly converts time values to the systems default time zone for display purposes.
     *
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAppointmentByName (String search) {
        ObservableList<Appointment> nameSearchAppts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM client_schedule.appointments WHERE Title LIKE ?";
        ZoneId localZone = ZoneId.systemDefault();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //convert UTC datetimes from SQL table to the user's local times for display
                ZonedDateTime utcStartTime = resultSet.getObject("Start", ZonedDateTime.class);
                ZonedDateTime localStartTime = utcStartTime.withZoneSameInstant(localZone);
                LocalDateTime displayStartTime = localStartTime.toLocalDateTime();

                ZonedDateTime utcEndTime = resultSet.getObject("End", ZonedDateTime.class);
                ZonedDateTime localEndTime = utcEndTime.withZoneSameInstant(localZone);
                LocalDateTime displayEndTime = localEndTime.toLocalDateTime();

                ZonedDateTime utcCreateTime = resultSet.getObject("Create_Date", ZonedDateTime.class);
                ZonedDateTime localCreateTime = utcCreateTime.withZoneSameInstant(localZone);
                LocalDateTime displayCreateTime = localCreateTime.toLocalDateTime();

                //get last update time and see if it needs to be converted
                ZonedDateTime utcUpdateTime = resultSet.getObject("Last_Update", ZonedDateTime.class);
                if (utcUpdateTime != null) {
                    ZonedDateTime localUpdateTime = utcUpdateTime.withZoneSameInstant(localZone);
                    LocalDateTime displayUpdateTime = localUpdateTime.toLocalDateTime();

                    Appointment appointment = new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            displayStartTime,
                            displayEndTime,
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            displayUpdateTime,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    );

                    nameSearchAppts.add(appointment);
                    //not all Appointments have been updated, so allow for null update time
                } else {
                    Appointment appointment = new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            displayStartTime,
                            displayEndTime,
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            null,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    );

                    nameSearchAppts.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return nameSearchAppts;
    }

    /**
     * Gets a string representing the customers activity status based on  whether the most
     * recent appointment date for the customer whose ID is passed as a parameter is within the last
     * 6 months (183 days), or if the customer has an appointment scheduled for the future.
     * If so, the customer is considered Active. Explicitly converts time values to the systems default time
     *  zone for display purposes.
     *
     * @return String either "new", "active", or "inactive"
     */
    public static String getCustomerStatus(int customerId) {
        String sql = "SELECT MAX(Start) AS MostRecentStart FROM client_schedule.appointments WHERE Customer_ID = ?";
        ZoneId localZone = ZoneId.systemDefault();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ZonedDateTime utcMostRecentStart = resultSet.getObject("MostRecentStart", ZonedDateTime.class);

                if (utcMostRecentStart != null) {
                    // Convert the most recent start time from UTC to local time
                    ZonedDateTime localMostRecentStart = utcMostRecentStart.withZoneSameInstant(localZone);
                    LocalDate localMostRecentStartDate = localMostRecentStart.toLocalDate();

                    // Calculate the date 183 days ago from now
                    LocalDate now = LocalDate.now();
                    LocalDate sixMonthsAgo = now.minusDays(183);

                    if (localMostRecentStartDate.isAfter(now)) {
                        // This explicitly checks for future appointments
                        return "active";
                    } else if (localMostRecentStartDate.isAfter(sixMonthsAgo)) {
                        // This checks for recent past activity within the last 183 days
                        return "active";
                    } else {
                        // No recent or future appointments
                        return "inactive";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // No appointment found, customer must be new
        return "new";
    }
}
