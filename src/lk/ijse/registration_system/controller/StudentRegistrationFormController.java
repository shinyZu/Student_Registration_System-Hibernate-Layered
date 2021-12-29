package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.custom.StudentBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.RegistrationDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.entity.Student;
import lk.ijse.registration_system.util.DateTimeUtil;
import lk.ijse.registration_system.util.NavigationUtil;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class StudentRegistrationFormController extends DateTimeUtil {

    private final StudentBO studentBO = (StudentBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.STUDENT);
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

        StudentDTO studentDTO =  studentBO.getStudentDetails(cmbStudentId.getValue());
        txtStudentName.setText(studentDTO.getStudentName());
        txtAddress.setText(studentDTO.getAddress());
        datePicker.getEditor().setText(formatter.format(studentDTO.getDob()));
        txtAge.setText(String.valueOf(studentDTO.getAge()));
        txtEmail.setText(studentDTO.getEmail());
        txtContactNo.setText(studentDTO.getContactNo());
        cmbProgram.getSelectionModel().clearSelection();
        cmbProgram.setPromptText("Training Program");
        txtProgramFee.setText("0.0");
        txtUpfrontFee.setText("0.0");

        ArrayList<CustomDTO> dtoList = studentBO.getRegisteredPrograms(cmbStudentId.getValue());
        System.out.println("dtoList: " +dtoList);

        for (CustomDTO dto : dtoList ) {
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
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.1", ButtonType.OK).show();

                    } else {
                        System.out.println(4);
                        new Alert(Alert.AlertType.WARNING, "Something went wrong. \nTry Again...", ButtonType.OK).show();
                    }

                } else { // if the Student is a new student
                    System.out.println(5);
                    if (studentBO.registerStudent(studentDTO, registrationDTO)) {
                        loadStudentIDs();
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
}
