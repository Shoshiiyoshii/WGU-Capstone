package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.FirstLevelDivision;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class manages the CRUD to the mysql database for First Level Division objects.
 */
public class FirstLevelDivisionDao {

    /**
     * Querys the client_schedule.first_level_divisions table to get a column of
     * strings containing all of the division IDs and division names.
     * The result is a string in the format "Division_ID, Division".
     * Used to display first level divisions so that both ID and name can be used to
     * select a first level division easily.
     *
     * @param whichDiv integer representing which set of divisions, a country ID
     * @return ObservableList of strings in the format "Division_ID, Division"
     */
    public static ObservableList<String> getDivList(int whichDiv) {
        ObservableList<String> results = FXCollections.observableArrayList();

        if (whichDiv > 0) {
            String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                    "FROM client_schedule.first_level_divisions WHERE Country_ID = ?;";
            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, whichDiv);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    results.add(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        } else {
            String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                    "FROM client_schedule.first_level_divisions;";
            try {
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
        }
        return results;
    }

    /**
     * Queries the client_schedule.first_level_divisions table and returns
     * the name of a specific division based on its ID.
     *
     * @param divId ID of first level division whose name is needed
     * @return String First Level Division name
     */
    public static String getDivName(int divId) {
        String divName = "";

        String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, divId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                divName = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divName;
    }
}

