package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.Main;

import java.sql.*;
import java.io.*;
import java.time.ZoneId;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * The LogInController class managed the log in window and
 * the record of log in attempts
 */
public class LogInController implements Initializable {
    @FXML
    private Label usernameLabel, passwordLabel, errorMsg, welcome, locationLabel, zoneId;
    @FXML
    private Button logInButton, closeButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    public static String loggedInUser;
    private ResourceBundle resourceBundle;
    Stage stage;

    /**
     * When the login button is clicked, the username and password inputs are checked
     * to see if they are valid. If they are, the landing page window is launched.
     * If now, an error message is displayed. Each log in attempt calls the
     * recordLogins() method.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void clickLogIn(ActionEvent event) throws IOException, SQLException {
        errorMsg.setVisible(false);
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();

        boolean userExists = isValidUser(usernameInput, passwordInput);
        if(userExists){
            recordLogins(usernameInput, true);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Customer and Appointment Management Portal");
            stage.setScene(scene);
            stage.show();
        } else{
            recordLogins(usernameInput, false);
            errorMsg.setVisible(true);
        }
    }

    /**
     * This method does a query against the client_schedule.users table, checking
     * if the user input it has been passed is valid.
     *
     * @param user username from textField
     * @param pass password from textField
     * @return boolean true/false depending on if the username/password combo exist
     * @throws SQLException
     */
   private boolean isValidUser(String user, String pass) throws SQLException{
       String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ? AND Password = ?";
       int loggedInUserId;
       try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2,pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                loggedInUserId = resultSet.getInt("User_ID");
                loggedInUser = user + ", ID: " + loggedInUserId;
                return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * recordLogins() writes to the login_activity.txt file and is called every
     * time the login button is clicked. It records the username that was attempted,
     * the time of the attempt in UTC and the system default local time, and
     * whether or not the log in was successful.
     *
     * @param username username from user input
     * @param loggedIn boolean true/false depending on if the login attempt was successful
     */
    public static void recordLogins(String username, boolean loggedIn) {
        ZonedDateTime utcNow = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime localNow = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        String utcTimestamp = utcNow.format(formatter);
        String localTimestamp = localNow.format(formatter);

        String loginAttempt = String.format("Login Attempt Time: %s - %s, Username Input: %s, Login Successful: %s%n",
                utcTimestamp, localTimestamp, username, loggedIn);

        String filePath = "login_activity.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(loginAttempt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the window when the close button is clicked.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method sets the resource bundle so that the log in window displays the form
     * in English or French based on the user’s computer language setting.
     * Allows all the text, labels, buttons, and errors on the form to be translated
     * appropriately
     *
     * @param resourceBundle prepared files for what to display depending on the computers language setting
     */
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * The initialize method is called first when the window opens, and
     * sets/uses the resource bundle to display the form
     * in English or French based on the user’s computer language setting.
     *
     * @param url
     * @param resourceBundle
     */
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