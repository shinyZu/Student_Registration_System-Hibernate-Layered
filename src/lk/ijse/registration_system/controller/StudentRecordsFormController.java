package lk.ijse.registration_system.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.custom.StudentDetailBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.util.DateTimeUtil;
import lk.ijse.registration_system.util.NavigationUtil;
import lk.ijse.registration_system.view.tm.RegistrationTM;
import lk.ijse.registration_system.view.tm.StudentDetailTM;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentRecordsFormController extends DateTimeUtil {

    private final StudentDetailBO studentDetailBO = (StudentDetailBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.STUDENT_DETAIL);
    private final ArrayList<StudentDetailTM> tmStudentList = new ArrayList<>();
    private final ArrayList<RegistrationTM> tmRegistrationList = new ArrayList<>();
    public AnchorPane contextStudentDetail;
    public TableView<StudentDetailTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colDOB;
    public TableColumn colAge;
    public TableColumn colEmail;
    public TableColumn colContactNo;
    public TableView<RegistrationTM> tblRegistration;
    public TableColumn colStudentID;
    public TableColumn colProgramId;
    public TableColumn colProgram;
    public TableColumn colDuration;
    public TableColumn colProgramFee;
    public TableColumn colDateOfRegistry;
    public TableColumn colUpfrontFee;
    public TextField txtSearchByStudent;
    public TextField txtSearchByProgram;
    private URL resource;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public void initialize() {
        loadDateAndTime();
        initStudentTable();
        initRegistrationTable();

        txtSearchByStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchStudentDetails(newValue);
            }
        });

        txtSearchByProgram.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchRegistrationDetails(newValue);
            }
        });
    }

    private void initRegistrationTable() {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colProgramFee.setCellValueFactory(new PropertyValueFactory<>("programFee"));
        colDateOfRegistry.setCellValueFactory(new PropertyValueFactory<>("dateOfRegistry"));
        colUpfrontFee.setCellValueFactory(new PropertyValueFactory<>("upfrontFee"));

        loadRegistrationDetails();
    }

    private void initStudentTable() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        loadStudentDetails();
    }

    private void loadRegistrationDetails() {
        ArrayList<CustomDTO> allRegistrations = studentDetailBO.getAllRegistrations();
        tblRegistration.getItems().clear();
        tmRegistrationList.clear();

        //System.out.println(allRegistrations);

        for (CustomDTO dto : allRegistrations) {
            tmRegistrationList.add(new RegistrationTM(
                    dto.getStudentId(),
                    dto.getProgramId(),
                    dto.getProgramName(),
                    dto.getDuration(),
                    dto.getProgramFee(),
                    //formatter.format(dto.getDateOfRegistry()),
                    String.valueOf(dto.getDateOfRegistry()),
                    dto.getUpfrontFee()
            ));
        }

        tblRegistration.setItems(FXCollections.observableArrayList(tmRegistrationList));
        allRegistrations.clear();
    }

    private void loadStudentDetails() {
        ArrayList<StudentDTO> allStudents = studentDetailBO.getAllStudents();
        tblStudent.getItems().clear();
        tmStudentList.clear();

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (StudentDTO dto : allStudents) {
            tmStudentList.add(new StudentDetailTM(
                    dto.getStudentId(),
                    dto.getStudentName(),
                    dto.getAddress(),
                    //dto.getDob(),
                    formatter.format(dto.getDob()),
                    dto.getAge(),
                    dto.getEmail(),
                    dto.getContactNo()
            ));
        }

        tblStudent.setItems(FXCollections.observableArrayList(tmStudentList));
        allStudents.clear();

    }

    private void searchStudentDetails(String text) {
        ArrayList<StudentDTO> students = studentDetailBO.searchStudent(text);
        ObservableList<StudentDetailTM> studentDetails = FXCollections.observableArrayList();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (StudentDTO dto : students) {
            studentDetails.add(new StudentDetailTM(
                    dto.getStudentId(),
                    dto.getStudentName(),
                    dto.getAddress(),
                    formatter.format(dto.getDob()),
                    dto.getAge(),
                    dto.getEmail(),
                    dto.getContactNo()

            ));
        }
        tblStudent.getItems().setAll(studentDetails);
    }

    private void searchRegistrationDetails(String text) {
        ArrayList<CustomDTO> registrations = studentDetailBO.searchRegistration(text);
        ObservableList<RegistrationTM> records = FXCollections.observableArrayList();

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (CustomDTO dto : registrations) {
            records.add(new RegistrationTM(
                    dto.getStudentId(),
                    dto.getProgramId(),
                    dto.getProgramName(),
                    dto.getDuration(),
                    dto.getProgramFee(),
                    formatter.format(dto.getDateOfRegistry()),
                    dto.getUpfrontFee()

            ));
        }
        tblRegistration.getItems().setAll(records);
    }

    public void goToDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/DashboardForm.fxml");
        NavigationUtil.navigateToPage(resource, contextStudentDetail);
    }
}
