package lk.ijse.registration_system.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationUtil {
    public static void navigateToPage(URL resource, AnchorPane context) throws IOException {
        Stage window = (Stage) context.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(FXMLLoader.load(resource)));
        window.show();
    }
}
