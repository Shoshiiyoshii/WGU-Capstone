package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;

import java.sql.*;

public class ContactDao {
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
}
