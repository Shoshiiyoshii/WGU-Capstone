package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Customer;

import java.sql.*;
import java.time.*;

/**
 * This class manages the CRUD to the mysql database for Customer objects.
 */
public class CustomerDao {
    public static ObservableList<Customer> displayCust = FXCollections.observableArrayList();
    public static  ObservableList<String> allCustIds = FXCollections.observableArrayList();

    /**
     * This method adds customers to the client_schedule.customers table and to
     * the ObservableList displayCust which is responsible for the display
     * of customers on the landing page. Time values are explicitly set to UTC
     * for storage.
     *
     * @param customer the customer to be added
     * @return boolean true/false whether add was successful
     * @throws SQLException
     */
    public static boolean addCust(Customer customer) throws SQLException{
        String sql = "INSERT INTO client_schedule.customers" +
                " (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID)" +
                " VALUES(?,?,?,?,?,?,?)";

        ZoneId userTimeZone = ZoneId.systemDefault();

        String custName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        //records the current instant of time in UTC, which is the correct time zone for my SQL data
        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC"));
        LocalDateTime created = createDate.withZoneSameInstant(userTimeZone).toLocalDateTime();

        String createdBy = customer.getCreatedBy();
        int divisionId = customer.getDivisionId();

        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setObject(5, createDate);
            preparedStatement.setString(6, createdBy);
            preparedStatement.setInt(7, divisionId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // The insertion was successful, there are generated keys
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    customer.setCustomerId(customerId);
                    customer.setCreateDate(created);
                    displayCust.add(customer);
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
     * Updates the customer with the same ID as the customer object it's passed
     * in the client_schedule.customers table in mySql and in the
     * ObservableList displayCust.Time values are explicitly set to UTC
     * for storage.
     *
     * @param customer customer to be updated
     * @param index location of customer to be updated in displayCust list
     * @return boolean true/false update was successful
     * @throws SQLException
     */
    public static boolean updateCust(Customer customer, int index) throws SQLException{
        int custId = customer.getCustomerId();

        ZoneId userTimeZone = ZoneId.systemDefault();

        String custName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        //records the current instant of time in UTC, which is the correct time zone for my SQL data
        ZonedDateTime updateDate = ZonedDateTime.now(ZoneId.of("UTC"));
        LocalDateTime updated = updateDate.withZoneSameInstant(userTimeZone).toLocalDateTime();

        String updatedBy = customer.getLastUpdatedBy();
        int divisionId = customer.getDivisionId();

        String sql = "UPDATE client_schedule.customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                " Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setObject(5, updateDate);
            preparedStatement.setString(6, updatedBy);
            preparedStatement.setInt(7, divisionId);
            preparedStatement.setInt(8, custId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // The insertion was successful
                    customer.setLastUpdate(updated);
                    displayCust.set(index, customer);
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Deletes a customer from the client_schedule.customers table in mySql
     * and from the ObservableList displayCust. Calls findCustAppts method to
     * and only allows delete if the selected customer has no appointments.
     *
     * @param customer appointment to delete
     * @return boolean true/false whether delete was successful
     * @throws SQLException
     */
    public static boolean deleteCust(Customer customer) throws SQLException{
        int custId = customer.getCustomerId();
        int appts = findCustAppts(custId);

        if(appts == 0) {
            String sql = "DELETE FROM client_schedule.customers WHERE CUSTOMER_ID = ?";

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, custId);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    displayCust.remove(customer);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
            return false;
    }

    /**
     * Gets all appointments scheduled for a selected customer, and returns an integer
     * indicating the number of appointments currently scheduled for the selected customer.
     * User by the deleteCust method to ensure that customers who have appointments scheduled
     * can't be deleted until their appointments are.
     *
     * @param custId id of customer whose appointments need to be searched
     * @return int representing the number of appointments scheduled for the customer
     */
    public static int findCustAppts(int custId){
        String sql = "SELECT * FROM client_schedule.appointments WHERE CUSTOMER_ID = ?";
        int appts = 0;
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, custId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                appts++;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return appts;
    }

    /**
     * Gets a list of all customers in the client_schedule.customers table. Used to populate
     * the ObservableList displayCust. Explictly converts time values to the systems
     * defalut time zone for display purposes.
     *
     * @return ObservableList of all customers
     */
        public static ObservableList<Customer> getAllCust() {
        String sql = "SELECT * FROM client_schedule.customers";
        ZoneId localZone = ZoneId.systemDefault();

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //convert UTC datetimes in SQL table to the user's local times for display
                //convert Create_Date
                ZonedDateTime utcCreateTime = resultSet.getObject("Create_Date", ZonedDateTime.class);
                ZonedDateTime localCreateTime = utcCreateTime.withZoneSameInstant(localZone);
                LocalDateTime displayCreateTime = localCreateTime.toLocalDateTime();

                //convert Last_Update
                ZonedDateTime utcUpdateTime = resultSet.getObject("Last_Update", ZonedDateTime.class);
                if(utcUpdateTime != null) {
                    ZonedDateTime localUpdateTime = utcUpdateTime.withZoneSameInstant(localZone);
                    LocalDateTime displayUpdateTime = localUpdateTime.toLocalDateTime();

                    Customer customer = new Customer(
                            resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            displayUpdateTime,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Division_ID"),
                            CountryDao.getCountryID(resultSet.getInt("Division_ID"))
                    );

                    displayCust.add(customer);
                    //not all Customers have been updated, so allow for null update time
                } else {
                    Customer customer = new Customer(
                    resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            displayCreateTime,
                            resultSet.getString("Created_By"),
                            null,
                            resultSet.getString("Last_Updated_By"),
                            resultSet.getInt("Division_ID"),
                            CountryDao.getCountryID(resultSet.getInt("Division_ID"))
                    );

                    displayCust.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return displayCust;
    }

    /**
     * Queries the client_schedule.customers table to retrieve an
     * ObservableList of all existing customer IDs.
     *
     * @return ObservableList of customer IDs
     */
    public static ObservableList<String> getAllCustId() {
        String sql = "SELECT Customer_ID FROM client_schedule.customers";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                allCustIds.add( String.valueOf(id));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allCustIds;
    }
}
