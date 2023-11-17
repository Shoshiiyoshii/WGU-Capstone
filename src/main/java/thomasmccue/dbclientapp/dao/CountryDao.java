package thomasmccue.dbclientapp.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;

import java.sql.*;

/**
 * This class manages the CRUD for country objects.
 */
public class CountryDao {

    /**
     * This method queries the client_schedule.countries table to return the name of
     * each country that the business is working in.
     *
     * @return ObservableList of country names
     */
        public static ObservableList<String> getAllCountries() {
            String sql = "SELECT Country FROM client_schedule.countries";
            ObservableList<String> results = FXCollections.observableArrayList();

            try{
                Connection connection = JDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    results.add(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return results;
        }

    /**
     * Used to get the correct country based on a first level division ID.
     *
     * @param divId the ID of the first level division whose country is needed
     * @return A string representing the name of the requested Country
     */
    protected static String getCountryID(int divId) {
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
