package thomasmccue.dbclientapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thomasmccue.dbclientapp.helper.JDBC;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Determine the user's preferred locale based on computer settings
        Locale userLocale = Locale.getDefault();
        // Load the appropriate resource bundle based on the user's locale
        ResourceBundle resourceBundle = ResourceBundle.getBundle("thomasmccue.dbclientapp.logInText", userLocale);

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logIn.fxml"));

        // Set the resource bundle for the loader
        fxmlLoader.setResources(resourceBundle);

        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void stop() throws Exception{
        JDBC.closeConnection();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
    }
}