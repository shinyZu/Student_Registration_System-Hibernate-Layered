package lk.ijse.registration_system.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.util.NavigationUtil;

import java.io.IOException;
import java.net.URL;

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
}
