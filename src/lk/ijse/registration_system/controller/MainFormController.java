package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.business.custom.VerifyUserBO;
import lk.ijse.registration_system.dto.LoginDTO;

import java.io.IOException;

public class MainFormController {
    public JFXButton btnLogin;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXPasswordField fieldPassword;
    public MaterialDesignIconView iconShowPassword;
    public MaterialDesignIconView iconHidePassword;

    VerifyUserBO verifyUserBO = (VerifyUserBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.VERIFY_USER);

    public void initialize() {
        txtPassword.setVisible(false);
        iconHidePassword.setVisible(false);
    }

    private String fPassword = null;
    public void showPasswordOnAction(MouseEvent mouseEvent) {
        iconShowPassword.setVisible(false);
        iconHidePassword.setVisible(true);

        txtPassword.setText(fieldPassword.getText());
        txtPassword.setVisible(true);
        txtPassword.setLabelFloat(true);

        fieldPassword.setLabelFloat(false);
        fieldPassword.setVisible(false);
        //validateHiddenPwdTextField();
        fieldPassword.toBack();
        this.fPassword = fieldPassword.getText();
        fieldPassword.clear();
    }

    public void hidePasswordOnAction(MouseEvent mouseEvent) {
        iconHidePassword.setVisible(false);
        iconShowPassword.setVisible(true);

        fieldPassword.setVisible(true);
        fieldPassword.setText(txtPassword.getText());
       // validateFieldOnKeyRelease(event);
        fieldPassword.setLabelFloat(true);

        txtPassword.setVisible(false);
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) btnLogin.getScene().getWindow();

        if (fieldPassword.getText().equals("")) {
            this.fPassword = txtPassword.getText();
        } else if (txtPassword.getText().equals("")) {
            this.fPassword = fieldPassword.getText();
        }

        LoginDTO loginDTO = new LoginDTO(
                txtUsername.getText(),
                fPassword
        );

        //System.out.println("loginDTO: "+loginDTO);

        if(verifyUserBO.isVerifiedUser(loginDTO)){
            window.close();
            window = new Stage();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            window.show();


        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password...").show();
            return;
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        if (fieldPassword.getText().equals("")) {
            this.fPassword = txtPassword.getText();
        } else if (txtPassword.getText().equals("")) {
            this.fPassword = fieldPassword.getText();
        }

        LoginDTO loginDTO = new LoginDTO(
                txtUsername.getText(),
                fPassword
        );

        if(verifyUserBO.isVerifiedUser(loginDTO)){
            new Alert(Alert.AlertType.WARNING, "User already Exist.....").show();
            return;
        }

        /*System.out.println(txtUsername.getText());
        System.out.println(fPassword);*/

        if (txtUsername.getText().isEmpty() || fPassword == null) {
            new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password2...").show();
            return;
        }

        if (verifyUserBO.checkForPasswordAvailability(loginDTO)) {
            Stage window = (Stage) btnLogin.getScene().getWindow();
            window.close();

            verifyUserBO.signUpAsNewUser(loginDTO);

            window = new Stage();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            window.show();

        } else {
            new Alert(Alert.AlertType.WARNING, "Password not available...").show();
            return;
        }
    }
}
