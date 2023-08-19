package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static thomasmccue.dbclientapp.helper.JDBC.connection;

public class AppointmentDao {

    private static ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    public static boolean addAppt(Appointment newAppt){
        if(apptList.contains(newAppt)){
            return false;
        }else {
            apptList.add(newAppt);
            try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client_schedule.appointments " +
                    "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                    " Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)")){
                preparedStatement.setString(1,newAppt.getTitle());
                preparedStatement.setString(2, newAppt.getDesc());
                preparedStatement.setString(3, newAppt.getLocation());
                preparedStatement.setString(4, newAppt.getType());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(newAppt.getStart()));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(newAppt.getEnd()));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(newAppt.getCreateDate()));
                preparedStatement.setString(8, newAppt.getCreatedBy());
                preparedStatement.setTimestamp(9, Timestamp.valueOf(newAppt.getLastUpdateTime()));
                preparedStatement.setString(10, newAppt.getLastUpdatedBy());
                preparedStatement.setInt(11, newAppt.getCustId());
                preparedStatement.setInt(12, newAppt.getUserId());
                preparedStatement.setInt(13, newAppt.getContactId());

                preparedStatement.executeUpdate();

                try(PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT MAX(Appointment_ID) AS " +
                        "LatestAppointmentID FROM client_schedule.appointments")){
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (resultSet.next()) {
                        newAppt.setApptId(resultSet.getInt("LatestAppointmentID"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
            return true;
    }

    public static boolean deleteAppt(Appointment selectedAppt) throws SQLException {
        if(apptList.contains(selectedAppt)) {
            apptList.remove(selectedAppt);
            try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM client_schedule.appointments" +
                    " WHERE Appointment_ID = ?")){
                preparedStatement.setInt(1,selectedAppt.getApptId());
            }catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        }else{
            return false;
        }
    }

    public static boolean editAppt(Appointment oldAppt, Appointment updatedAppt){
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client_schedule.appointments SET Title = ?," +
                " Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?," +
                " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?")){
            preparedStatement.setString(1, updatedAppt.getTitle());
            preparedStatement.setString(2, updatedAppt.getDesc());
            preparedStatement.setString(3, updatedAppt.getLocation());
            preparedStatement.setString(4, updatedAppt.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(updatedAppt.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(updatedAppt.getEnd()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(updatedAppt.getLastUpdateTime()));
            preparedStatement.setString(8, updatedAppt.getLastUpdatedBy());
            preparedStatement.setInt(9, updatedAppt.getCustId());
            preparedStatement.setInt(10, updatedAppt.getUserId());
            preparedStatement.setInt(11, updatedAppt.getContactId());
            preparedStatement.setInt(12, oldAppt.getApptId());

            int numberApptsUpdated = preparedStatement.executeUpdate();

            if (numberApptsUpdated == 0){
                return false;
            }

            int index = apptList.indexOf(oldAppt);
            if (index != -1) {
                apptList.set(index, updatedAppt);
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        apptList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client_schedule.appointments");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            //for every row in mysql table Appointments, create an appointment object and add to the ObservableList apptList
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setApptId(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDesc(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appointment.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                appointment.setCreatedBy(resultSet.getString("Created_By"));
                appointment.setLastUpdate(resultSet.getObject("Last_Update", LocalDateTime.class));
                appointment.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                appointment.setCustId(resultSet.getInt("Customer_ID"));
                appointment.setUserId(resultSet.getInt("User_ID"));
                appointment.setContactId(resultSet.getInt("Contact_ID"));

                //prevent duplicate appointments
                if(!apptList.contains(appointment)) {
                    apptList.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return apptList;
    }

    public static ObservableList<Appointment> getThisWeeksAppointments() {
        ObservableList<Appointment> weekAppt = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client_schedule.appointments WHERE WEEK(Start)" +
                " = WEEK(NOW()) AND YEAR(Start) = YEAR(NOW());");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            //for every row in mysql table Appointments, create an appointment object and add to the ObservableList apptList
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setApptId(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDesc(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appointment.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                appointment.setCreatedBy(resultSet.getString("Created_By"));
                appointment.setLastUpdate(resultSet.getObject("Last_Update", LocalDateTime.class));
                appointment.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                appointment.setCustId(resultSet.getInt("Customer_ID"));
                appointment.setUserId(resultSet.getInt("User_ID"));
                appointment.setContactId(resultSet.getInt("Contact_ID"));

                weekAppt.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return weekAppt;
    }

    public static ObservableList<Appointment> getThisMonthsAppointments() {
        ObservableList<Appointment> monthAppt = FXCollections.observableArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client_schedule.appointments WHERE MONTH(Start) " +
                "= MONTH(NOW()) AND YEAR(Start) = YEAR(NOW())");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            //for every row in mysql table Appointments, create an appointment object and add to the ObservableList apptList
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setApptId(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDesc(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appointment.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appointment.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                appointment.setCreatedBy(resultSet.getString("Created_By"));
                appointment.setLastUpdate(resultSet.getObject("Last_Update", LocalDateTime.class));
                appointment.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                appointment.setCustId(resultSet.getInt("Customer_ID"));
                appointment.setUserId(resultSet.getInt("User_ID"));
                appointment.setContactId(resultSet.getInt("Contact_ID"));

                monthAppt.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return monthAppt;
    }
}

