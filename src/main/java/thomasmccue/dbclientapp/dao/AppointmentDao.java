package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Appointment;
import thomasmccue.dbclientapp.model.Customer;

import java.sql.*;
import java.time.*;

public class AppointmentDao {

    public static ObservableList<Appointment> displayAppt = FXCollections.observableArrayList();

    public static boolean addAppt(Appointment appointment) {
        String sql = "INSERT INTO client_schedule.appointments " +
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By," +
                " Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String title = appointment.getTitle();
        String description = appointment.getDesc();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String createdBy = appointment.getCreatedBy();
        int customerID = appointment.getCustId();
        int userID = appointment.getUserId();
        int contactID = appointment.getContactId();
        //get create date as this instant in UTC
        Instant now = Instant.now();
        Timestamp createDate = Timestamp.from(now);


        Timestamp apptStart = Timestamp.valueOf(appointment.getStart());
        Timestamp apptEnd = Timestamp.valueOf(appointment.getEnd());

        //check that appointment start and end times meet requirements
        boolean validTime = apptTimesValid(apptStart, apptEnd);
        if (validTime) {
            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setTimestamp(5, apptStart);
                preparedStatement.setTimestamp(6, apptEnd);
                preparedStatement.setTimestamp(7, createDate);
                preparedStatement.setString(8, createdBy);
                preparedStatement.setInt(11, customerID);
                preparedStatement.setInt(12, userID);
                preparedStatement.setInt(13, contactID);

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
        }
        return false;
    }

      /*  public static boolean deleteAppt (Appointment selectedAppt) throws SQLException {
            if (apptList.contains(selectedAppt)) {
                apptList.remove(selectedAppt);
                try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM client_schedule.appointments" +
                        " WHERE Appointment_ID = ?")) {
                    preparedStatement.setInt(1, selectedAppt.getApptId());
                    System.out.println("Success");
                } catch (SQLException e) {
                    System.out.println("fail");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return true;
            } else {
                return false;
            }
        }
    }

        public static boolean editAppt (Appointment oldAppt, Appointment updatedAppt){
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client_schedule.appointments SET Title = ?," +
                    " Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?," +
                    " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?")) {
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

                if (numberApptsUpdated == 0) {
                    return false;
                }

                int index = apptList.indexOf(oldAppt);
                if (index != -1) {
                    apptList.set(index, updatedAppt);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        }*/

        public static ObservableList<Appointment> getAllAppointments () {
            String sql = "SELECT * FROM client_schedule.appointments";

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    //convert UTC datetimes in SQL table to the user's local times for display
                    //convert Create_Date, Start and End
                    Timestamp utcStartTime = resultSet.getTimestamp("Start");
                    LocalDateTime localStartTime = utcStartTime.toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    Timestamp utcEndTime = resultSet.getTimestamp("End");
                    LocalDateTime localEndTime = utcEndTime.toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    Timestamp utcCreateTime = resultSet.getTimestamp("Create_Date");
                    LocalDateTime localCreateTime = utcCreateTime.toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    //get last update time and see if it needs to be converted
                    Timestamp utcUpdateTime = resultSet.getTimestamp("Last_Update");
                    if (utcUpdateTime != null) {
                        LocalDateTime localUpdateTime = utcUpdateTime.toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        Appointment appointment = new Appointment(
                                resultSet.getInt("Appointment_ID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Location"),
                                resultSet.getString("Type"),
                                localStartTime,
                                localEndTime,
                                localCreateTime,
                                resultSet.getString("Created_By"),
                                localUpdateTime,
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
                                localStartTime,
                                localEndTime,
                                localCreateTime,
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

       /* public static ObservableList<Appointment> getThisWeeksAppointments () {
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
        }*/

       /* public static ObservableList<Appointment> getThisMonthsAppointments () {
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
        }*/

        public static boolean apptTimesValid (Timestamp start, Timestamp end){
            ZoneId et = ZoneId.of("America/New_York");

            Instant startInstant = start.toInstant();
            Instant endInstant = end.toInstant();

            ZonedDateTime etStart = ZonedDateTime.ofInstant(startInstant, et);
            ZonedDateTime etEnd = ZonedDateTime.ofInstant(endInstant, et);

            ZonedDateTime businessOpen = etStart.with(LocalTime.of(8, 0));
            ZonedDateTime businessClosed = etStart.with(LocalTime.of(22, 0));

            boolean isWithinBusinessHours = !etStart.isBefore(businessOpen) && !etStart.isAfter(businessClosed)
                    && !etEnd.isBefore(businessOpen) && !etEnd.isAfter(businessClosed);

            boolean isStartTimeBeforeEndTime = etStart.isBefore(etEnd);


            return isWithinBusinessHours && isStartTimeBeforeEndTime;
        }
    }

