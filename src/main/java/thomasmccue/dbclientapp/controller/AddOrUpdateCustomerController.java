package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        custIdField.setText("This Customer ID hasn't yet been assigned");
    }
    //setup, different depending on whether modify or add button are clicked
    public void setUpModify(String titleText, String buttonText) throws IOException{
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);
    }

    public void selectOrEnterCountry(ActionEvent event)throws IOException {
        String selected = custCountrySelectionBox.getValue();
        if(selected.equals("United States")){
            ObservableList<String> states = FXCollections.observableArrayList(
                    "Alabama",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "District of Columbia",
            "Florida",
            "Georgia",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming",
            "Hawaii",
            "Alaska");


            custFirstLvlDivisionSelectionBox.setItems(states);
        } else if (selected.equals("United Kingdom")) {
            ObservableList<String> ukStates = FXCollections.observableArrayList(
                    "England",
            "Wales",
            "Scotland",
            "Northern Ireland");

            custFirstLvlDivisionSelectionBox.setItems(ukStates);
        } else if (selected.equals("Canada")){
            ObservableList<String> provinces = FXCollections.observableArrayList(
                    "Northwest Territories",
            "Alberta",
            "British Columbia",
            "Manitoba",
            "New Brunswick",
            "Nova Scotia",
            "Prince Edward Island",
            "Ontario",
            "Québec",
            "Saskatchewan",
            "Nunavut",
            "Yukon",
            "Newfoundland and Labrador");

            custFirstLvlDivisionSelectionBox.setItems(provinces);
        } else{
            ObservableList<String> all = FXCollections.observableArrayList(

                    "Alabama",
                    "Arizona",
                    "Arkansas",
                    "California",
                    "Colorado",
                    "Connecticut",
                    "Delaware",
                    "District of Columbia",
                    "Florida",
                    "Georgia",
                    "Idaho",
                    "Illinois",
                    "Indiana",
                    "Iowa",
                    "Kansas",
                    "Kentucky",
                    "Louisiana",
                    "Maine",
                    "Maryland",
                    "Massachusetts",
                    "Michigan",
                    "Minnesota",
                    "Mississippi",
                    "Missouri",
                    "Montana",
                    "Nebraska",
                    "Nevada",
                    "New Hampshire",
                    "New Jersey",
                    "New Mexico",
                    "New York",
                    "North Carolina",
                    "North Dakota",
                    "Ohio",
                    "Oklahoma",
                    "Oregon",
                    "Pennsylvania",
                    "Rhode Island",
                    "South Carolina",
                    "South Dakota",
                    "Tennessee",
                    "Texas",
                    "Utah",
                    "Vermont",
                    "Virginia",
                    "Washington",
                    "West Virginia",
                    "Wisconsin",
                    "Wyoming",
                    "Hawaii",
                    "Alaska",
                    "England",
                    "Wales",
                    "Scotland",
                    "Northern Ireland",
                    "Northwest Territories",
                    "Alberta",
                    "British Columbia",
                    "Manitoba",
                    "New Brunswick",
                    "Nova Scotia",
                    "Prince Edward Island",
                    "Ontario",
                    "Québec",
                    "Saskatchewan",
                    "Nunavut",
                    "Yukon",
                    "Newfoundland and Labrador");

            custFirstLvlDivisionSelectionBox.setItems(all);
        }

    }

    public void selectOrEnterFirstLvlDiv(ActionEvent event)throws IOException {

    }

    public void saveClicked(ActionEvent event)throws IOException {
        if(pageTitleLabel.getText().equals("Add A Customer To The System")) {
                String name = custNameField.getText();
                String phone = custPhoneField.getText();
                String address = custAddressField.getText();
                String postalCode = custPostalCodeField.getText();

                //ensure that all text fields have been filled out
                if(name.isEmpty() || phone.isEmpty() || address.isEmpty() || postalCode.isEmpty()){
                    errorMessage.setText("Please ensure that you have filled out the Customers name," +
                            " phone number, address, and postal code");
                } else{
                    customer.setCustomerName(custNameField.getText());
                    customer.setPhone(custPhoneField.getText());
                    customer.setAddress(custAddressField.getText());
                    customer.setPostalCode(custPostalCodeField.getText());

                    //customer.setDivisionId(); FIXME, create method somewhere to return the division ID
                }
        }/* else if (pageTitleLabel.getText().equals("FIXME")) {

        }*/
    }

    public void cancelClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList(
                "United States",
                "United Kingdom",
                "Canada");

        custCountrySelectionBox.setItems(countries);

    }
}
