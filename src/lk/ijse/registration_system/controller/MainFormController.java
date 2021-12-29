package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    public JFXButton btnLogin;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) btnLogin.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        window.show();
    }
}
