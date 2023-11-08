package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.FirstLevelDivision;

import java.sql.*;

public class FirstLevelDivisionDao {
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

    public static String getDivName(int divId){
        String divName = "";

        String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, divId);

            ResultSet resultSet = preparedStatement.executeQuery();
           if(resultSet.next()){
               divName = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divName;
    }

   /* public static int getDivId(String divDisplay){
        int divId = 0;

        return divId;
    }*/
}

