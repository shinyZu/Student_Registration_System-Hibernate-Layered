package lk.ijse.registration_system.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.custom.StudentDetailBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.ProgramDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.util.DateTimeUtil;
import lk.ijse.registration_system.util.NavigationUtil;
import lk.ijse.registration_system.view.tm.ProgramTM;
import lk.ijse.registration_system.view.tm.RegistrationTM;
import lk.ijse.registration_system.view.tm.StudentDetailTM;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentRecordsFormController extends DateTimeUtil {

    private final StudentDetailBO studentDetailBO = (StudentDetailBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.STUDENT_DETAIL);
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

    private final ArrayList<StudentDetailTM> tmStudentList = new ArrayList<>();
    private final ArrayList<RegistrationTM> tmRegistrationList = new ArrayList<>();
    private URL resource;

    public void initialize() {
        loadDateAndTime();
        initStudentTable();
        initRegistrationTable();
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

        System.out.println(allRegistrations);

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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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

    public void goToDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/DashboardForm.fxml");
        NavigationUtil.navigateToPage(resource, contextStudentDetail);
    }
}
