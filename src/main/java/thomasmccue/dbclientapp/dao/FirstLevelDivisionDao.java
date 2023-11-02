package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.FirstLevelDivision;

import java.sql.*;

public class FirstLevelDivisionDao {
      public static ObservableList<String> getDiv1List() {
          String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                  "FROM client_schedule.first_level_divisions WHERE Country_ID = ?;";
          ObservableList<String> usList = FXCollections.observableArrayList();

        try{
             Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1, 1);

             ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        usList.add(resultSet.getString(1));
                    }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return usList;
        }

    public static ObservableList<String> getDiv2List() {
        String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                "FROM client_schedule.first_level_divisions WHERE Country_ID = ?;";
        ObservableList<String> ukList = FXCollections.observableArrayList();

        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 2);

            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ukList.add(resultSet.getString(1));
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ukList;
    }

    public static ObservableList<String> getDiv3List() {
        String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                "FROM client_schedule.first_level_divisions WHERE Country_ID = ?;";
        ObservableList<String> canList = FXCollections.observableArrayList();

        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 3);

            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    canList.add(resultSet.getString(1));
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return canList;
    }

    public static ObservableList<String> getAllDivList() {
        String sql = "SELECT CONCAT_WS(', ', Division_ID, Division) " +
                "FROM client_schedule.first_level_divisions;";

        ObservableList<String> allList = FXCollections.observableArrayList();

        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    allList.add(resultSet.getString(1));
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allList;
    }
}

