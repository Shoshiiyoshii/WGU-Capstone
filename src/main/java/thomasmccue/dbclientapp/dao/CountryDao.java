package thomasmccue.dbclientapp.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;

import java.sql.*;
public class CountryDao {
        public static ObservableList<String> getAllCountries() {
            String sql = "SELECT Country FROM client_schedule.countries";
            ObservableList<String> results = FXCollections.observableArrayList();

            try (Connection connection = JDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(resultSet.getString(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            System.out.println(results);
            return results;
        }
}
