package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Customer;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CustomerDao {
    public static boolean addCust(Customer customer) {
        String sql = "INSERT INTO client_schedule.customers" +
                " (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID)" +
                " VALUES(?,?,?,?,?,?,?)";

        String custName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        //records the current instant of time in UTC, which is the correct time zone for my SQL data
        Instant now = Instant.now();
        Timestamp createDate = Timestamp.from(now);

        String createdBy = customer.getCreatedBy();
        int divisionId = customer.getDivisionId();

        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, createDate);
            preparedStatement.setString(6, createdBy);
            preparedStatement.setInt(7, divisionId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // The insertion was successful, there are generated keys
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    //is this necessary? FIXME
                    customer.setCustomerId(customerId);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void updateCust(Customer customer) {
        int custId = customer.getCustomerId();
        String sql = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String custName = customer.getCustomerName();
            String address = customer.getAddress();
            String postalCode = customer.getPostalCode();
            String phone = customer.getPhone();

            //records the current instant of time in UTC, which is the correct time zone for my SQL data
            Instant now = Instant.now();
            Timestamp updateDate = Timestamp.from(now);

            String updatedBy = customer.getLastUpdatedBy();
            int divisionId = customer.getDivisionId();

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, updateDate);
            preparedStatement.setString(6, updatedBy);
            preparedStatement.setInt(7, divisionId);
            preparedStatement.setInt(8, custId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void deleteCust(Customer customer) {
        int custId = customer.getCustomerId();
        String sql = "DELETE FROM client_schedule.customers WHERE CUSTOMER_ID = ?";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, custId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Customer findCust(Customer customer) {
        Customer foundCust = null;
        int custId = customer.getCustomerId();
        String sql = "SELECT * FROM client_schedule.customers WHERE CUSTOMER_ID =  ?";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, custId);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    //convert UTC datetimes in SQL table to the user's local times for display
                    //convert Create_Date
                    Timestamp utcCreateTime = resultSet.getTimestamp("Create_Date");
                    LocalDateTime localCreateTime = utcCreateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    //convert Last_Update
                    Timestamp utcUpdateTime = resultSet.getTimestamp("Last_Update");
                    LocalDateTime localUpdateTime = utcUpdateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    foundCust = new Customer(
                            resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            localCreateTime,
                            resultSet.getString("Created_By"),
                            localUpdateTime,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Division_ID"),
                            getCountryID(resultSet.getInt("Division_ID"))
                    );
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return foundCust;
    }

        public static ObservableList<Customer> getAllCust() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.customers";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //convert UTC datetimes in SQL table to the user's local times for display
                //convert Create_Date
                Timestamp utcCreateTime = resultSet.getTimestamp("Create_Date");
                LocalDateTime localCreateTime = utcCreateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                //convert Last_Update
                Timestamp utcUpdateTime = resultSet.getTimestamp("Last_Update");
                if(utcUpdateTime != null) {
                    LocalDateTime localUpdateTime = utcUpdateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    Customer customer = new Customer(
                            resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            localCreateTime,
                            resultSet.getString("Created_By"),
                            localUpdateTime,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Division_ID"),
                            getCountryID(resultSet.getInt("Division_ID"))
                    );

                    custList.add(customer);
                    //not all Customers have been updated, so allow for null update time
                } else {
                    Customer customer = new Customer(
                    resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            localCreateTime,
                            resultSet.getString("Created_By"),
                            null,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Division_ID"),
                            getCountryID(resultSet.getInt("Division_ID"))
                    );

                    custList.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return custList;
    }

    private static String getCountryID(int divId) {
        int countryId = 0;
        String country = "";

        String sql = "SELECT Country_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, divId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countryId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String sql2 = "SELECT Country FROM client_schedule.countries WHERE Country_ID = ?";
        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setInt(1, countryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return country;
    }
}
