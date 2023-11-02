package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.Main;

import java.sql.*;
import java.time.ZoneId;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LogInController implements Initializable {
    @FXML
    private Label usernameLabel, passwordLabel, errorMsg, welcome, locationLabel, zoneId;
    @FXML
    private Button logInButton, closeButton;
    @FXML
    private TextField passwordField, usernameField;

    private static int loggedInUserId;
    private ResourceBundle resourceBundle;

    Stage stage;

    @FXML
    public void clickLogIn(ActionEvent event) throws IOException {
        errorMsg.setVisible(false);
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();

        boolean userExists = isValidUser(usernameInput, passwordInput);
        if(userExists){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Customer and Appointment Management Portal");
            stage.setScene(scene);
            stage.show();
        } else{
            errorMsg.setVisible(true);
        }
    }

   private boolean isValidUser(String user, String pass){
        try{
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2,pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                loggedInUserId = resultSet.getInt("User_ID");
                return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static int getLoggedInUserId(){
        return loggedInUserId;
    }

    @FXML
    public void clickClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set ResourceBundle to one passed from Main
        setResourceBundle(resourceBundle);

        // Retrieve the translations from the resource bundle
        String usernameLabelText = resourceBundle.getString("logIn.label.usernameLabel");
        String passwordLabelText = resourceBundle.getString("logIn.label.passwordLabel");
        String logInButtonText = resourceBundle.getString("logIn.button.logInButton");
        String errorMsgText = resourceBundle.getString("logIn.label.errorMsg");
        String welcomeText = resourceBundle.getString("logIn.label.welcome");
        String locationLabelText = resourceBundle.getString("logIn.label.locationLabel");
        String closeButtonText = resourceBundle.getString("logIn.button.closeButton");
        String zoneIdText = ZoneId.systemDefault().getId();

        // Set the text for the FXML elements
        welcome.setText(welcomeText);
        locationLabel.setText(locationLabelText);
        zoneId.setText(zoneIdText);
        usernameLabel.setText(usernameLabelText);
        passwordLabel.setText(passwordLabelText);
        logInButton.setText(logInButtonText);
        closeButton.setText(closeButtonText);

        //set error message text to correct language, but also set it to invisible.
        // Will be set to visible when there is an error later.
        errorMsg.setVisible(false);
        errorMsg.setText(errorMsgText);
    }
}