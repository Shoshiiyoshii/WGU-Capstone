package thomasmccue.dbclientapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thomasmccue.dbclientapp.helper.JDBC;

import java.sql.*;

public class UserDao {
        public static ObservableList<String> getAllUserId() {
            String sql = "SELECT User_ID FROM client_schedule.users";
            ObservableList<String> allContactIds = FXCollections.observableArrayList();

            try {
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    allContactIds.add(String.valueOf(id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return allContactIds;
        }
}
