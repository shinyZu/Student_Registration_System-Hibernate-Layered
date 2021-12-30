package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.business.custom.VerifyUserBO;
import lk.ijse.registration_system.dto.LoginDTO;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class MainFormController {
    public JFXButton btnLogin;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXPasswordField fieldPassword;
    public MaterialDesignIconView iconShowPassword;
    public MaterialDesignIconView iconHidePassword;
    
    public JFXButton btnSignUp;
    public Label lblLoginTitle;
    public Label lblSignUpTitle;
    public Label lblSignUp;
    public Label lblLogin;
    public Label lblNewUser;
    public Label lblAlreadyRegistered;

    VerifyUserBO verifyUserBO = (VerifyUserBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.VERIFY_USER);

    private KeyEvent event;

    public void initialize() {
        txtPassword.setVisible(false);
        iconHidePassword.setVisible(false);

        txtUsername.setOnKeyReleased(event -> {
            this.event = event;
            validateFieldOnKeyRelease(event);
        });

        fieldPassword.setOnKeyReleased(event -> {
            this.event = event;
            validateFieldOnKeyRelease(event);
        });

        txtPassword.setOnKeyReleased(event -> {
            this.event = event;
            fieldPassword.setText(txtPassword.getText());
            fPassword=fieldPassword.getText();
            validateFieldOnKeyRelease(event);
            validateHiddenPwdTextField();
        });
        mapValidations();
    }

    LinkedHashMap<TextField, Pattern> mapLoginDetails = new LinkedHashMap<>();
    String userNameRegEx = "^[U][0-9]{3}$"; //U001, U002
    String userPwdRegEx = "^[U][0-9]{3}[@][a-z]{3,6}$"; // U001@ijse
    Pattern userNamePtn = Pattern.compile(userNameRegEx);
    Pattern userPwdPtn = Pattern.compile(userPwdRegEx);

    private void mapValidations() {
        mapLoginDetails.put(txtUsername,userNamePtn);
        mapLoginDetails.put(fieldPassword,userPwdPtn);
    }

    public void validateFieldOnKeyRelease(KeyEvent keyEvent) {
        Object response = null;
        response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                ((TextField) response).requestFocus();

            } else if (response instanceof Boolean) {
                //
            }
        }
    }

    private Object validate() {
        btnLogin.setDisable(true);
        btnSignUp.setDisable(true);

        for (TextField keyTextField: mapLoginDetails.keySet()) {
            Pattern valuePattern = mapLoginDetails.get(keyTextField);
            if (!valuePattern.matcher(keyTextField.getText()).matches()) {
                if (!keyTextField.getText().isEmpty()) {
                    keyTextField.getStylesheets().clear();
                    keyTextField.getStylesheets().add(getClass().getResource("../view/assets/styles/invalidInput.css").toString());
                }
                return keyTextField;
            }
            keyTextField.getStylesheets().clear();
            keyTextField.getStylesheets().add(getClass().getResource("../view/assets/styles/validInput.css").toString());
        }
        if (btnLogin.isVisible()){
            btnLogin.setDisable(false);
        } else{
            btnSignUp.setDisable(false);
        }
        return true;
    }

    private void validateHiddenPwdTextField() {
        boolean pwdMatches = Pattern.matches(userPwdRegEx, txtPassword.getText());
        txtPassword.getStylesheets().clear();
        if (pwdMatches) {
            txtPassword.getStylesheets().add("lk/ijse/registration_system/view/assets/styles/validInput.css");
            //btnLogin.requestFocus();
        } else {
            txtPassword.getStylesheets().add("lk/ijse/registration_system/view/assets/styles/invalidInput.css");
            fieldPassword.getStylesheets().add("lk/ijse/registration_system/view/assets/styles/validInput.css");
        }
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
        validateHiddenPwdTextField();
        fieldPassword.toBack();
        this.fPassword = fieldPassword.getText();
        fieldPassword.clear();
    }

    public void hidePasswordOnAction(MouseEvent mouseEvent) {
        iconHidePassword.setVisible(false);
        iconShowPassword.setVisible(true);

        fieldPassword.setVisible(true);
        fieldPassword.setText(txtPassword.getText());
        validateFieldOnKeyRelease(event);
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

        System.out.println("fieldPassword: "+fieldPassword.getText());
        System.out.println("txtPassword: "+txtPassword.getText());
        System.out.println("fPassword: "+fPassword);

        LoginDTO loginDTO = new LoginDTO(
                txtUsername.getText(),
                fPassword
        );

        if(verifyUserBO.isVerifiedUser(loginDTO)){
            window.close();
            window = new Stage();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            window.show();


        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid Username or Password...").show();
            return;
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        if (fieldPassword.getText().equals("")) {
            this.fPassword = txtPassword.getText();
        } else if (txtPassword.getText().equals("")) {
            this.fPassword = fieldPassword.getText();
        }

        System.out.println("fieldPassword: "+fieldPassword.getText());
        System.out.println("txtPassword: "+txtPassword.getText());
        System.out.println("fPassword: "+fPassword);

        LoginDTO loginDTO = new LoginDTO(
                txtUsername.getText(),
                fPassword
        );

        if(verifyUserBO.isVerifiedUser(loginDTO)){
            new Alert(Alert.AlertType.WARNING, "User already Exist.....").show();
            return;
        }

        if (txtUsername.getText().isEmpty() || fPassword == null) {
            new Alert(Alert.AlertType.WARNING, "Invalid Username or Password...").show();
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

    public void displaySignUpFormOnAction(MouseEvent mouseEvent) {
        clearFields();

        lblSignUpTitle.setVisible(true);
        lblLoginTitle.setVisible(false);

        btnSignUp.setVisible(true);
        btnLogin.setVisible(false);

        lblAlreadyRegistered.setVisible(true);
        lblNewUser.setVisible(false);

        lblLogin.setVisible(true);
        lblSignUp.setVisible(false);

        btnSignUp.setDisable(false);
    }

    public void displayLoginFormOnAction(MouseEvent mouseEvent) {
        clearFields();

        lblLoginTitle.setVisible(true);
        lblSignUpTitle.setVisible(false);

        btnLogin.setVisible(true);
        btnSignUp.setVisible(false);

        lblNewUser.setVisible(true);
        lblAlreadyRegistered.setVisible(false);

        lblSignUp.setVisible(true);
        lblLogin.setVisible(false);

        btnLogin.setDisable(false);

    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        fieldPassword.clear();
        fPassword = null;

        for (TextField field: mapLoginDetails.keySet()) {
            field.getStylesheets().clear();
        }

        fieldPassword.getStylesheets().clear();
    }
}
