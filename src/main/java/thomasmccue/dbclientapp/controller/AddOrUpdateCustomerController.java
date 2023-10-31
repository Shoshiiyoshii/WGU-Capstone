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
import thomasmccue.dbclientapp.dao.CountryDao;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.FirstLevelDivisionDao;
import thomasmccue.dbclientapp.helper.JDBC;
import thomasmccue.dbclientapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void setUpModify(String titleText, String buttonText, Customer customer) throws IOException{
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);

        //prefill fields
        custIdField.setText(Integer.toString(customer.getCustomerId()));
        custNameField.setText(customer.getCustomerName());
        custPhoneField.setText(customer.getPhone());
        custAddressField.setText(customer.getAddress());
        custPostalCodeField.setText(customer.getPostalCode());
        custFirstLvlDivisionSelectionBox.setValue(Integer.toString(customer.getDivisionId()));


    }

  public void selectOrEnterCountry(ActionEvent event) throws IOException, SQLException {
        String selected = custCountrySelectionBox.getValue();
            //depending on what country is selected, show the corresponding first level divisions in the combo box
            if("United States".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDiv1List());
            } else if ("United Kingdom".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDiv2List());
            } else if ("Canada".equals(selected)) {
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getDiv3List());
            } else{
                custFirstLvlDivisionSelectionBox.setItems(FirstLevelDivisionDao.getAllDivList());
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
        custCountrySelectionBox.setItems(CountryDao.getAllCountries());
    }
}
