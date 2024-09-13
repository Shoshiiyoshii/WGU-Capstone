package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.Main;
import thomasmccue.dbclientapp.dao.CountryDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.FirstLevelDivisionDao;
import thomasmccue.dbclientapp.model.Customer;
import thomasmccue.dbclientapp.model.NewCustomer;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.*;

/**
 * This class serves as a controller and manages
 * the addOrUpdateCustomer.fxml. Depending on
 * whether the Add or Update buttons were used to access
 * it from the landing page, it behaves differently.
 */
public class AddOrUpdateCustomerController implements Initializable {
    @FXML
    private Label custIdLabel, pageTitleLabel, custNameLabel, custPhoneLabel, custAddressLabel, custCountryLabel;
    @FXML
    private Label custFirstLvlDivisionLabel, custPostalCodeLabel, errorMessage;
    @FXML
    private TextField custIdField, custNameField, custPhoneField, custAddressField, custPostalCodeField;
    @FXML
    private ComboBox<String> custFirstLvlDivisionSelectionBox, custCountrySelectionBox;
    @FXML
    private Button saveButton, cancelButton;

    private Customer customer;

    /**
     * This method is called from the landing page if the Add button below the
     * customers table is clicked. It sets up the form for adding a customer,
     * and populates the first level division box with all first level divisions.
     *
     * Once a country is selected, the first level division will be filtered, that
     * is handled in a different method.
     *
     * @param titleText to set the title of the add form
     * @param buttonText to set the save button to say "Save"
     * @throws IOException
     */
    public void setUpAdd(String titleText, String buttonText) throws IOException{
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);
        custIdField.setText("Customer ID will be auto-assigned on save");
        custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(-1));
    }
    /**
     * This method is called from the landing page if the Update button below the
     * customers table is clicked. It sets up the form for editing a customer that
     * has been selected from the customers table on the landing page.
     * This method also pre-fills the form with the correct customers information, and
     * pre-populates the first level division box with the correctly filtered first
     * level divisions based on the customers country.
     *
     * @param titleText to set the title of the modify form
     * @param buttonText to set the save button to say "Save Update"
     * @param customer the customer that has been selected to be edited
     * @throws IOException
     */
    public void setUpModify(String titleText, String buttonText, Customer customer) throws IOException{
        this.customer = customer;
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);

        custCountrySelectionBox.setValue(customer.getCountry());

        int divId = customer.getDivisionId();
        String divName = FirstLevelDivisionDao.getDivName(divId);
        String concatenatedValue = divId + ", " + divName;

        custFirstLvlDivisionSelectionBox.getSelectionModel().select(concatenatedValue);
        if(custCountrySelectionBox.getValue().equals("U.S")){
            custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(1));
        }else if(custCountrySelectionBox.getValue().equals("UK")){
            custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(2));
        }else if(custCountrySelectionBox.getValue().equals("Canada")){
            custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(3));
        } else {
            custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(-1));
            }

        custIdField.setText(Integer.toString(customer.getCustomerId()));
        custNameField.setText(customer.getCustomerName());
        custPhoneField.setText(customer.getPhone());
        custAddressField.setText(customer.getAddress());
        custPostalCodeField.setText(customer.getPostalCode());
    }

    /**
     * This method is triggered when the user selects a country from the combo box.
     * When a country is selected, the first level division combo box drop down option will
     * automatically be filtered to only show the first level divisions that are in the
     * selected country.
     *
     * @param event
     * @throws IOException
     */
  public void selectOrEnterCountry(ActionEvent event) throws IOException{
        String selected = custCountrySelectionBox.getValue();
            //depending on what country is selected, show the corresponding first level divisions in the combo box
            if("United States".equals(selected) || "U.S".equals(selected) || "U.S.A".equals(selected) || "U.S.".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(1));
            } else if ("UK".equals(selected) || "United Kingdom".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(2));
            } else if ("Canada".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(3));
            } else{
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(-1));
            }
    }

    public void selectOrEnterFirstLvlDiv(ActionEvent event)throws IOException {

    }

    /**
     * This method is called when the save button is clicked. It behaves differently if
     * the form has been accessed form the add button or update button on the landing page.
     *
     * If accessed from the add button, user input is collected from the form, and checked
     * to ensure that the form has been completed. If needed, an error message is displayed
     * in the GUI prompting the user to first complete the form. If the form is correctly
     * filled out, the values are used to create a new customer object, which is then passed
     * to the CustomerDao class to be added to the database.
     *
     * if accessed from the modify button on the landing page, the form is still checks to ensure
     * that it has been filled out properly, and then the user input is sent to the selected
     * customers mutators/setters. Once the customer object has been updated, it is sent to
     * the CustomerDao class to be updated in database.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void saveClicked(ActionEvent event) throws IOException, SQLException {
        //in add mode, save a new customer
        if (pageTitleLabel.getText().equals("Add A Customer To The System")) {
            String name = custNameField.getText();
            String phone = custPhoneField.getText();
            String address = custAddressField.getText();
            String postalCode = custPostalCodeField.getText();

            //ensure that all text fields have been filled out
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || postalCode.isEmpty()) {
                errorMessage.setText("Please ensure that you have filled out the Customers name," +
                        " phone number, address, and postal code");
            } else {
                String divS = custFirstLvlDivisionSelectionBox.getValue();
                int divId = parseDivId(divS);

                String country = custCountrySelectionBox.getValue();

                String createdBy = LogInController.loggedInUser;

                customer = new NewCustomer(
                        name,
                        address,
                        postalCode,
                        phone,
                        null,
                        createdBy,
                        null,
                        null,
                        divId,
                        country
                );
                boolean added = CustomerDao.addCust(customer);
                if (added) {
                    Stage currentStage = (Stage) (saveButton.getScene().getWindow());
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    currentStage.setScene(scene);
                    currentStage.setTitle("Customer and Appointment Management Portal");
                    currentStage.centerOnScreen();
                    currentStage.show();
                } else {
                    errorMessage.setText("There was a problem adding the customer to the database.");
                }
            }
            //in update mode, update an existing customer
        } else if (pageTitleLabel.getText().equals("Modify An Existing Customer")) {
            int index = CustomerDao.displayCust.indexOf(customer);

            String name = custNameField.getText();
            String phone = custPhoneField.getText();
            String address = custAddressField.getText();
            String postalCode = custPostalCodeField.getText();

            //ensure that all text fields have been filled out
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || postalCode.isEmpty()) {
                errorMessage.setText("Please ensure that you have filled out the Customers name," +
                        " phone number, address, and postal code");
            } else {
                String divS = custFirstLvlDivisionSelectionBox.getValue();
                int divId = parseDivId(divS);

                String country = custCountrySelectionBox.getValue();

                String updatedBy = LogInController.loggedInUser;

                customer.setCustomerName(name);
                customer.setPhone(phone);
                customer.setAddress(address);
                customer.setPostalCode(postalCode);
                customer.setDivisionId(divId);
                customer.setCountry(country);
                customer.setLastUpdatedBy(updatedBy);

                boolean updated = CustomerDao.updateCust(customer,index);
                if (updated) {
                    Stage currentStage = (Stage) (saveButton.getScene().getWindow());
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    currentStage.setScene(scene);
                    currentStage.setTitle("Customer and Appointment Management Portal");
                    currentStage.centerOnScreen();
                    currentStage.show();
                } else {
                    errorMessage.setText("There was a problem updating the customer in the database.");
                }
            }
        }
    }

    /**
     * This method closes the Update or Add window when the cancel button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void cancelClicked(ActionEvent event)throws IOException {
        Stage currentStage = (Stage) (cancelButton.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        currentStage.setScene(scene);
        currentStage.setTitle("Customer and Appointment Management Portal");
        currentStage.centerOnScreen();
        currentStage.show();

    }

    /**
     * The first level division combo box is populated with both the Division_ID
     * and the name of the division. When a division is selected from the comboBox,
     * the string representing the Division_ID and the name is sent here to be parsed
     * so that the Division_ID can be returned on its own to be used.
     *
     * @param divS String representing the select division. Format "Division_ID, Name"
     * @return int divId, the Division_ID separated from the division name
     */
    private int parseDivId(String divS){
        int divId = 0;
        Pattern p = Pattern.compile("\\d+");

        // Create a matcher for the input string
        Matcher m = p.matcher(divS);

        // Find and extract numbers
        while (m.find()) {
            String numberStr = m.group(); // Get the matched number as a string
            divId = Integer.parseInt(numberStr); // Convert the string to an integer
        }

        return divId;
    }
    /**
     * initialize method is called first, ensures that the country comboBox is populated
     * correctly when the form opens.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custCountrySelectionBox.setItems(CountryDao.getAllCountries());
    }
}
