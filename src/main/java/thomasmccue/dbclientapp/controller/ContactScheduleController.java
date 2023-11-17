package thomasmccue.dbclientapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.dao.ContactDao;
import thomasmccue.dbclientapp.model.ContactAppts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class acts as a controller for the contactSchedule.fxml file
 * and controls which contacts schedule is visible.
 */
public class ContactScheduleController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private ComboBox<String> selectContactBox;
    @FXML
    private TableView<ContactAppts> apptTable;
    @FXML
    private TableColumn<ContactAppts, Integer> apptIdCol, custIdTopCol;
    @FXML
    private TableColumn<ContactAppts, String> titleCol, descCol, typeCol, startDTCol, endDTCol;

    /**
     * This method is activated when a contact is selected from the drop-down menu
     * of the contacts comboBox. When a contact is selected, the contacts ID is
     * sent to the ContactDao and the Observable list of that contacts appointments
     * is returned and used to populate a table for display.
     * @param event
     */
    public void selectContact(ActionEvent event) {
        String contact = selectContactBox.getValue();
        //split the string at the comma
        String[] parts = contact.split(",");
        //parse the first part (after trimming spaces) to an integer
        int contactId = Integer.parseInt(parts[0].trim());

        apptTable.setItems(ContactDao.getContactAppts(contactId));

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDTCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDTCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdTopCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
    }
    /**
     * This method closes the window when the cancel button is clicked.
     *
     * @param event
     * @throws IOException
     */
    public void exitClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * When the window is launched, the initialize method sets the contacts
     * combo box so that the drop-down is populated with a list of all
     * contacts.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectContactBox.setItems(ContactDao.getListOfContacts());

    }
}
