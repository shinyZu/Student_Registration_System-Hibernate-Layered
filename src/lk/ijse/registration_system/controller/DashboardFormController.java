package lk.ijse.registration_system.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.util.NavigationUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class DashboardFormController {
    public AnchorPane contextDashboard;
    private URL resource;

    public void loadRegistrationFormOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/StudentRegistrationForm.fxml");
        NavigationUtil.navigateToPage(resource, contextDashboard);
    }

    public void loadProgramFormOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/ProgramRegistrationForm.fxml");
        NavigationUtil.navigateToPage(resource, contextDashboard);
    }

    public void loadStudentRecordsOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/StudentRecordsForm.fxml");
        NavigationUtil.navigateToPage(resource, contextDashboard);
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        resource = getClass().getResource("../view/MainForm.fxml");


        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Logout?", yes, no);
        alert.setTitle("Confirmation Alert");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            NavigationUtil.navigateToPage(resource, contextDashboard);

        } else {
            //
        }
    }
}
