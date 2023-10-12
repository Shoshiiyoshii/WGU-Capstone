package thomasmccue.dbclientapp.dao;

import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Customer;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static thomasmccue.dbclientapp.helper.JDBC.connection;

public class CustomerDao {
    public void addCust(Customer customer) {
        Connection connection = JDBC.getConnection();
        String sql = "INSERT INTO client_schedule.customers" +
                " (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID)" +
                " VALUES(?,?,?,?,?,?)";

        String custName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        //records the current instant of time in UTC, which is the correct time zone for my sql data
        Instant now = Instant.now();
        Timestamp createDate = Timestamp.from(now);

        String createdBy = customer.getCreatedBy();
        int divisionId = customer.getDivisionId();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3,postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, createDate);
            preparedStatement.setString(6, createdBy);
            preparedStatement.setInt(7, divisionId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBC.closeConnection();
        }
    }

    public void updateCust(Customer customer){
        Connection connection = JDBC.getConnection();
        int custId = customer.getCustomerId();
        String sql = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        String custName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        //records the current instant of time in UTC, which is the correct time zone for my sql data
        Instant now = Instant.now();
        Timestamp updateDate = Timestamp.from(now);

        String updatedBy = customer.getLastUpdatedBy();
        int divisionId = customer.getDivisionId();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3,postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, updateDate);
            preparedStatement.setString(6, updatedBy);
            preparedStatement.setInt(7, divisionId);
            preparedStatement.setInt(8, custId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBC.closeConnection();
        }
    }
    public void deleteCust(Customer customer) {
        Connection connection = JDBC.getConnection();
        int custId = customer.getCustomerId();
        String sql = "DELETE FROM client_schedule.customers WHERE CUSTOMER_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, custId);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBC.closeConnection();
        }
    }

    public Customer findCust(Customer customer){
        Connection connection = JDBC.getConnection();
        Customer foundCust = null;
        int custId = customer.getCustomerId();
        String sql = "SELECT * FROM client_schedule.customers WHERE CUSTOMER_ID =  ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, custId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    //convert UTC datetimes in sql table to users local times for display
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
                            resultSet.getInt("Division_ID")
                            );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBC.closeConnection();
        }
        return foundCust;
    }
}
