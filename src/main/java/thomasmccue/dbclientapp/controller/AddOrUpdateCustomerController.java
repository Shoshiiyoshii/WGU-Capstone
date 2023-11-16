package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.Main;
import thomasmccue.dbclientapp.dao.CountryDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.FirstLevelDivisionDao;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Customer;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.*;


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


    public void setUpAdd(String titleText, String buttonText) throws IOException{
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);
        custIdField.setText("Customer ID will be auto-assigned on save");
        custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDivList(-1));
    }

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

  public void selectOrEnterCountry(ActionEvent event) throws IOException, SQLException {
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

    public void saveClicked(ActionEvent event)throws IOException {
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

                customer = new Customer(
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
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
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
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                } else {
                    errorMessage.setText("There was a problem updating the customer in the database.");
                }
            }
        }
    }

    public void cancelClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custCountrySelectionBox.setItems(CountryDao.getAllCountries());

    }
}
