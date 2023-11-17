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
    public void exitClicked(ActionEvent event)throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectContactBox.setItems(ContactDao.getListOfContacts());

    }
}
