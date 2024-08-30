package thomasmccue.dbclientapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.CustomerDao;
import thomasmccue.dbclientapp.dao.FirstLevelDivisionDao;
import thomasmccue.dbclientapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the customerLocationReport.fxml display
 */
public class CustomerLocationReportController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private TableView<CustomerDivs> divisionTable;
    @FXML
    private TableView<CustomerCountries> countryTable;
    @FXML
    private TableColumn<CustomerDivs, String> divIdColumn;
    @FXML
    private TableColumn<CustomerDivs, Integer> custColumn1;
    @FXML
    private TableColumn<CustomerCountries, String> countryColumn;
    @FXML
    private TableColumn<CustomerCountries, Integer> custColumn2;

    private ObservableList<CustomerDivs> customersPerDiv = FXCollections.observableArrayList();
    private ObservableList<CustomerCountries> customersPerCountry = FXCollections.observableArrayList();

    /**
     * This method is used to populate the table showing how many customers live in
     * each first level division. For each customer that lives in each division, a
     * customer count is incremented. Then a customerDivs object is created for
     * each first level division, and an observable list of how many customers per
     * division is used to populate a tableView to display the report.
     */
    public void populateCustPerDiv(){
       ObservableList<String> allDivs = FirstLevelDivisionDao.getDivList(-1);

        for (String div: allDivs) {
            int custs = 0;
            //split the string at the comma
            String[] parts = div.split(",");
            //parse the first part (after trimming spaces) to an integer
            int divId = Integer.parseInt(parts[0].trim());
            for (Customer customer : CustomerDao.displayCust) {
                int custDiv = customer.getDivisionId();

                if(divId == custDiv){
                    custs++;
                }
            }
            CustomerDivs customerDivs = new CustomerDivs(div,custs);
            customersPerDiv.add(customerDivs);
        }
        divisionTable.setItems(customersPerDiv);

       divIdColumn.setCellValueFactory(new PropertyValueFactory<>("divName"));
       custColumn1.setCellValueFactory(new PropertyValueFactory<>("custs"));
    }

    /**
     * This method shows how many customers live in each country that the business operates in.
     * CustomerCountry objects are created and added to an observable list of customers per country
     * which is then displayed in the tableView.
     */
    public void populateCustPerCountry(){
        int usCustomers = 0;
        int ukCustomers = 0;
        int canadaCustomers = 0;
            for (Customer customer : CustomerDao.displayCust) {
                int custDiv = customer.getDivisionId();
                if(custDiv >= 1 && custDiv <= 54) {
                    usCustomers++;
                } else if(custDiv >= 60 && custDiv <= 72) {
                    canadaCustomers++;
                } else if (custDiv >= 101 && custDiv <= 104) {
                    ukCustomers++;
                }
            }
            CustomerCountries customersInUs = new CustomerCountries("U.S", usCustomers);
            CustomerCountries customersInUK = new CustomerCountries("UK", ukCustomers);
            CustomerCountries customersInCanada = new CustomerCountries("Canada", canadaCustomers);
            customersPerCountry.add(customersInUs);
            customersPerCountry.add(customersInUK);
            customersPerCountry.add(customersInCanada);

            countryTable.setItems(customersPerCountry);

            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            custColumn2.setCellValueFactory(new PropertyValueFactory<>("custs"));
    }
    /**
     * This method closes the window when the cancel button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void exitClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The initialize method is called when the window launches, and calls
     * the populateCustPerDiv() and populateCustPerCountry() methods so that
     * the reports are displayed in the tableViews.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCustPerDiv();
        populateCustPerCountry();

    }
}
