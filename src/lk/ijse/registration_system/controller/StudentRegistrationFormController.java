package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.custom.StudentBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.RegistrationDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.entity.Registration;
import lk.ijse.registration_system.util.DateTimeUtil;
import lk.ijse.registration_system.util.NavigationUtil;
import lk.ijse.registration_system.util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class StudentRegistrationFormController extends DateTimeUtil {
    public AnchorPane contextRegistry;
    private URL resource;

    public ComboBox<String> cmbStudentId;
    public TextField txtStudentName;
    public TextField txtAddress;
    public TextField txtDOB;
    public JFXDatePicker datePicker;
    public TextField txtAge;
    public TextField txtEmail;
    public TextField txtContactNo;
    public ComboBox<String> cmbProgram;
    public JFXButton btnSearch;
    public JFXButton btnRegister;
    public TextField txtProgramFee;
    public ListView<String> listRegisteredPrograms;
    public TextField txtUpfrontFee;

    private final StudentBO studentBO = (StudentBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.STUDENT);
    private final ArrayList<String> programList = new ArrayList<>();

    public void initialize() {
        loadDateAndTime();
        loadStudentIDs();
        loadCmbPrograms();
        setStudentID();

        cmbProgram.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadTxtFee(newValue);
            }
        });

        mapValidations();
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    //Pattern idPattern = Pattern.compile("^[S][0-9]{3}$"); // S001, S002
    Pattern namePattern = Pattern.compile("^[A-Z][a-z/ ]{3,}[A-Z][a-z]{3,}|[A-Z][a-z]{3,}$");
    Pattern addressPattern = Pattern.compile("^[A-Z][a-z]{3,}[\\s]?[0-9]*$"); // Galle, Colombo 7
    Pattern agePattern = Pattern.compile("^[0-9]{1,2}$"); //1, 22
    Pattern emailPattern = Pattern.compile("^[A-z|0-9]{2,}@(gmail)(.com|.lk)$"); //kamal@gmail.com, amal123@gmail.lk
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$"); //0712345689
    Pattern upfrontFeePattern = Pattern.compile("^[0-9]{3,}(.0)|[0-9]{3,}$"); //15000

    private void mapValidations() {
        map.put(txtStudentName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtAge, agePattern);
        map.put(txtEmail, emailPattern);
        map.put(txtContactNo, contactPattern);
        map.put(txtUpfrontFee, upfrontFeePattern);
    }

    public void validateFieldOnKeyRelease(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //
            }
        }
    }

    private void setStudentID() {
        cmbStudentId.setValue(studentBO.generateStudentID());
    }

    private void loadStudentIDs() {
        cmbStudentId.getItems().clear();
        cmbStudentId.getItems().addAll(studentBO.getAllStudentIds());
    }

    private void loadCmbPrograms() {
        cmbProgram.getItems().addAll(studentBO.getProgramNames());
    }

    private void loadTxtFee(String programName) {
        txtProgramFee.setText(String.valueOf(studentBO.getProgramFee(programName)));
    }

    public void goToDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/DashboardForm.fxml");
        NavigationUtil.navigateToPage(resource, contextRegistry);
    }

    public void searchOnAction(ActionEvent actionEvent) {
        listRegisteredPrograms.getItems().clear();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        StudentDTO studentDTO = studentBO.getStudentDetails(cmbStudentId.getValue());

        if (studentDTO == null){
            new Alert(Alert.AlertType.WARNING, "No Student available...", ButtonType.OK).show();
            return;
        }

        txtStudentName.setText(studentDTO.getStudentName());
        txtAddress.setText(studentDTO.getAddress());
        datePicker.getEditor().setText(formatter.format(studentDTO.getDob()));
        txtAge.setText(String.valueOf(studentDTO.getAge()));
        txtEmail.setText(studentDTO.getEmail());
        txtContactNo.setText(studentDTO.getContactNo());
        cmbProgram.getSelectionModel().clearSelection();
        cmbProgram.setPromptText("Training Program");
        txtProgramFee.setText("0.0");
        txtUpfrontFee.setPromptText("0.0");
        txtUpfrontFee.clear();

        /**
         * If fetch type = Lazy in Student Entity
         * will have to explicitly request for registration details(child entity details)
         **/

        ArrayList<CustomDTO> dtoList = studentBO.getRegisteredPrograms(cmbStudentId.getValue());

        for (CustomDTO dto : dtoList) {
            listRegisteredPrograms.getItems().add(dto.getProgramName());
        }

        /**
         * If fetch type = Eager in Student Entity
         *
         * if te below code is executed while being on Lazy fetching mode
         * it will not bring any registration details(registered programs)
         * bcz in lazy fetching mode only parent data is fetched
         * */

        //System.out.println("studentDTO when searched: "+studentDTO);
        //System.out.println("getRegDetails when searched: "+studentDTO.getRegDetails());

        /*for (Registration reg : studentDTO.getRegDetails()) {
            listRegisteredPrograms.getItems().add(reg.getProgram().getProgramName());
        }*/
    }

    public void registerStudentOnAction(ActionEvent actionEvent){
        ArrayList<TextField> fields = new ArrayList();
        fields.add(txtStudentName);
        fields.add(txtAddress);
        fields.add(txtAge);
        fields.add(txtEmail);
        fields.add(txtContactNo);
        fields.add(txtUpfrontFee);

        for (TextField txt : fields) {
            if (txt.getText().isEmpty() || cmbProgram.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill in the required fields...", ButtonType.OK).show();
                return;
            }
        }

        /*System.out.println("getValue: "+datePicker.getValue());
        System.out.println("getEditor: "+datePicker.getEditor().getText());
        System.out.println(java.sql.Date.valueOf(datePicker.getEditor().getText()));
        System.out.println(java.sql.Date.valueOf(datePicker.getValue()));*/

        StudentDTO studentDTO = new StudentDTO(
                cmbStudentId.getValue(),
                txtStudentName.getText(),
                txtAddress.getText(),
                java.sql.Date.valueOf(datePicker.getEditor().getText()),
                //java.sql.Date.valueOf(datePicker.getValue()),
                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtContactNo.getText()
        );
        System.out.println("studentDTO: " + studentDTO);

        RegistrationDTO registrationDTO = new RegistrationDTO(
                java.sql.Date.valueOf(LocalDate.now()),
                Double.parseDouble(txtUpfrontFee.getText()),
                cmbStudentId.getValue(),
                studentBO.getProgramID(cmbProgram.getValue())
        );

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        if (txtUpfrontFee.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "No Payment done.\nPlease make the Payment to proceed registration.", ButtonType.OK).show();

        } else if (txtStudentName.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Please fill in the required fields...", ButtonType.OK).show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to continue Registration?", yes, no);
            alert.setTitle("Confirmation Alert");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                if (studentBO.studentExists(cmbStudentId.getValue())) { // if the Student is already registered
                    if (studentBO.registerStudent(registrationDTO)) {
                        clearFields();
                        cmbStudentId.setValue(studentBO.generateStudentID());
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.", ButtonType.OK).show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Something went wrong. \nTry Again...", ButtonType.OK).show();
                    }

                } else { // if the Student is a new student
                    if (studentBO.registerStudent(studentDTO, registrationDTO)) {
                        loadStudentIDs();
                        clearFields();
                        cmbStudentId.setValue(studentBO.generateStudentID());
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.", ButtonType.OK).show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Something went wrong. \nTry Again...", ButtonType.OK).show();
                    }
                }
            }
        }
    }

    private void clearFields() {
        txtStudentName.clear();
        txtAddress.clear();
        datePicker.getEditor().clear();
        txtAge.clear();
        txtEmail.clear();
        txtContactNo.clear();
        cmbProgram.getSelectionModel().clearSelection();
        cmbProgram.setPromptText("Training Program");
        txtProgramFee.setText("0.0");
        txtUpfrontFee.clear();
        txtUpfrontFee.setPromptText("0.0");
        listRegisteredPrograms.getItems().clear();

        for (TextField field: map.keySet()) {
            field.getParent().setStyle("-fx-border-color: black");
            ((AnchorPane) field.getParent()).getChildren().get(1).setStyle("-fx-text-fill: black");
        }
    }

    public void clearFieldsOnAction(ActionEvent actionEvent) {
        clearFields();
        cmbStudentId.setValue(studentBO.generateStudentID());
    }

    public void editStudentOnAction(ActionEvent actionEvent) {

        ArrayList<TextField> fields = new ArrayList();
        fields.add(txtStudentName);
        fields.add(txtAddress);
        fields.add(txtAge);
        fields.add(txtEmail);
        fields.add(txtContactNo);

        for (TextField field : fields) {
            System.out.println(field.getText().isEmpty());
            if (field.getText().isEmpty()) {
                System.out.println(field.getText());
                new Alert(Alert.AlertType.WARNING, "Please fill in the required fields...", ButtonType.OK).show();
                return;
            }
        }


        StudentDTO studentDTO = new StudentDTO(
                cmbStudentId.getValue(),
                txtStudentName.getText(),
                txtAddress.getText(),
                java.sql.Date.valueOf(datePicker.getEditor().getText()),
                //java.sql.Date.valueOf(datePicker.getValue()),
                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtContactNo.getText()
        );

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this Student?", yes, no);
        alert.setTitle("Confirmation Alert");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            if ( studentBO.updateStudent(studentDTO)) {
                clearFields();
                cmbStudentId.setValue(studentBO.generateStudentID());
                new Alert(Alert.AlertType.CONFIRMATION, "Student Updated Successfully.", ButtonType.OK).show();

            } else {
                new Alert(Alert.AlertType.WARNING, "Something went wrong. \nTry Again...", ButtonType.OK).show();
            }
        }
    }
}
