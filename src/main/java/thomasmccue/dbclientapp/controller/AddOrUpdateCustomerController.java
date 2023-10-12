package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddOrUpdateCustomerController implements Initializable {
    @FXML
    private Label custIdLabel, pageTitleLabel, custNameLabel, custPhoneLabel, custAddressLabel, custCountryLabel;
    @FXML
    private Label custFirstLvlDivisionLabel, custPostalCodeLabel;
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
       // custIdField.setText(String.valueOf(customer.getCustomerId()));
    }
    //setup, different depending on whether modify or add button are clicked
    public void setUpModify(String titleText, String buttonText) throws IOException{
        pageTitleLabel.setText(titleText);
        saveButton.setText(buttonText);
    }

    public void selectOrEnterFirstLvlDiv(ActionEvent event)throws IOException {

    }

    public void selectOrEnterCountry(ActionEvent event)throws IOException {

    }

    public void saveClicked(ActionEvent event)throws IOException {

    }

    public void cancelClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
