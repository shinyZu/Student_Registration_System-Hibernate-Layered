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

    private final StudentBO studentBO = (StudentBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.STUDENT);
    private final ArrayList<String> programList = new ArrayList<>();
    public AnchorPane contextRegistry;
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
    private URL resource;

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
    Pattern idPattern = Pattern.compile("^[S][0-9]{3}$"); // S001, S002
    Pattern namePattern = Pattern.compile("^[A-Z][a-z/ ]{3,}[A-Z][a-z]{3,}|[A-Z][a-z]{3,}$");
    Pattern addressPattern = Pattern.compile("^[A-Z][a-z]{3,}[\\s]?[0-9]*$"); // Galle, Colombo 7
    Pattern agePattern = Pattern.compile("^[0-9]{1,2}$"); //1, 22
    Pattern emailPattern = Pattern.compile("^[A-z|0-9]{2,}@(gmail)(.com|.lk)$"); //kamal@gmail.com, amal123@gmail.lk
    Pattern contactPattern = Pattern.compile("^[0-9]{10}$"); //0712345689
    Pattern upfrontFeePattern = Pattern.compile("^[0-9]{3,}$"); //15000

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
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
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

        ArrayList<CustomDTO> dtoList = studentBO.getRegisteredPrograms(cmbStudentId.getValue());
        System.out.println("dtoList: " + dtoList);

        for (CustomDTO dto : dtoList) {
            //programList.add(dto.getProgramName());
            listRegisteredPrograms.getItems().add(dto.getProgramName());
        }
        //System.out.println("programList: "+programList);
        //listRegisteredPrograms.setItems(FXCollections.observableArrayList(programList));

    }

    public void registerStudentOnAction(ActionEvent actionEvent) throws ParseException {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

        // System.out.println("date1: "+datePicker.getEditor().getText());
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //String date = formatter.format(datePicker.getEditor().getText());

        //String strDate = datePicker.getEditor().getText();
      /*  LocalDate value = datePicker.getValue();
        System.out.println("localeDtae value: "+value);*/

        //String strDate = "2021-12-14 00:00:00";
        /*String strDate = String.valueOf(value);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = sdf.parse(strDate);
        java.sql.Date sqlDate = new Date(date.getTime());
        System.out.println("sqlDate: "+sqlDate);*/

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

        StudentDTO studentDTO = new StudentDTO(
                //txtStudentId.getText(),
                cmbStudentId.getValue(),
                txtStudentName.getText(),
                txtAddress.getText(),
                java.sql.Date.valueOf(datePicker.getValue()),
                //java.sql.Date.valueOf(txtDOB.getText()),
                //java.sql.Date.valueOf(datePicker.getEditor().getText()),
                //formatter.parse(datePicker.getEditor().getText()),
                //java.sql.Date.valueOf(formatter.format(datePicker.getValue())),
                //java.sql.Date.valueOf(formatter.format(datePicker.getEditor().getText())),

                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtContactNo.getText()
        );
        System.out.println("studentDTO: " + studentDTO);

        RegistrationDTO registrationDTO = new RegistrationDTO(
                //LocalDate.now(),
                java.sql.Date.valueOf(LocalDate.now()),
                //java.sql.Date.valueOf(lblDate.getText()),
                /*String.valueOf(LocalDate.now()),*/
                Double.parseDouble(txtUpfrontFee.getText()),
                /*new StudentDTO(txtStudentId.getText()),
                new ProgramDTO(studentBO.getProgramID(cmbProgram.getValue()))*/
                //txtStudentId.getText(),
                cmbStudentId.getValue(),
                studentBO.getProgramID(cmbProgram.getValue())
        );

        System.out.println("registrationDTO: " + registrationDTO);

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
                System.out.println(1);
                if (studentBO.studentExists(cmbStudentId.getValue())) { // if the Student is already registered
                    System.out.println(2);
                    if (studentBO.registerStudent(registrationDTO)) {
                        System.out.println(3);
                        clearFields();
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.1", ButtonType.OK).show();

                    } else {
                        System.out.println(4);
                        new Alert(Alert.AlertType.WARNING, "Something went wrong. \nTry Again...", ButtonType.OK).show();
                    }

                } else { // if the Student is a new student
                    System.out.println(5);
                    if (studentBO.registerStudent(studentDTO, registrationDTO)) {
                        loadStudentIDs();
                        clearFields();
                        System.out.println(6);
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.2", ButtonType.OK).show();

                    } else {
                        System.out.println(7);
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


}
