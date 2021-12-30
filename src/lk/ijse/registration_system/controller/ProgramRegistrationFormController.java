package lk.ijse.registration_system.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.registration_system.business.BOFactory;
import lk.ijse.registration_system.business.custom.ProgramBO;
import lk.ijse.registration_system.dto.ProgramDTO;
import lk.ijse.registration_system.util.DateTimeUtil;
import lk.ijse.registration_system.util.NavigationUtil;
import lk.ijse.registration_system.util.ValidationUtil;
import lk.ijse.registration_system.view.tm.ProgramTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ProgramRegistrationFormController extends DateTimeUtil {
    public AnchorPane contextProgramRegistry;
    public JFXButton btnAddProgram;
    private URL resource;

    public TextField txtProgramId;
    public TextField txtProgram;
    public TextField txtDuration;
    public TextField txtFee;

    public TableView<ProgramTM> tblProgram;
    public TableColumn colProgramID;
    public TableColumn colProgram;
    public TableColumn colDuration;
    public TableColumn colFee;

    private final ProgramBO programBO = (ProgramBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.PROGRAM);
    private final ArrayList<ProgramTM> tmProgramList = new ArrayList<>();
    private int rowSelected = -1;

    public void initialize() {
        loadDateAndTime();
        setProgramID();
        initTable();

        tblProgram.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                //
            } else {
                loadDataToFields(newValue);
            }
        });

        tblProgram.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            rowSelected = (int) newValue;
        });

        mapValidations();
    }

    private void initTable() {
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadProgramsToTable();
    }

    private void loadProgramsToTable() {
        ArrayList<ProgramDTO> allPrograms = programBO.getAllPrograms();
        tblProgram.getItems().clear();
        tmProgramList.clear();

        for (ProgramDTO dto : allPrograms) {
            tmProgramList.add(new ProgramTM(
                    dto.getProgramId(),
                    dto.getProgramName(),
                    dto.getDuration(),
                    dto.getFee()
            ));
        }

        tblProgram.setItems(FXCollections.observableArrayList(tmProgramList));
        allPrograms.clear();
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern programPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern durationPattern = Pattern.compile("^[0-9\\s]+[a-z]{4,}$");
    Pattern feePattern = Pattern.compile("^[0-9]{3,}(.0)|[0-9]{3,}$");

    private void mapValidations() {
        map.put(txtProgram,programPattern);
        map.put(txtDuration,durationPattern);
        map.put(txtFee,feePattern);
    }

    public void validateFieldOnKeyRelease(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddProgram);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //
            }
        }
    }

    private void loadDataToFields(ProgramTM rowSelected) {
        txtProgramId.setText(rowSelected.getProgramId());
        txtProgram.setText(rowSelected.getProgramName());
        txtDuration.setText(rowSelected.getDuration());
        txtFee.setText(String.valueOf(rowSelected.getFee()));
    }

    private void setProgramID() {
        txtProgramId.setText(programBO.generateProgramID());
    }

    public void goToDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resource = getClass().getResource("../view/DashboardForm.fxml");
        NavigationUtil.navigateToPage(resource, contextProgramRegistry);
    }

    public void addProgramOnAction(ActionEvent actionEvent) {
        if (txtProgram.getText().equals("") || txtDuration.getText().equals("") || txtFee.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Please fill the fields...", ButtonType.OK).show();
            return;
        }

        if (programAlreadyExist(txtProgramId.getText())) {
            new Alert(Alert.AlertType.WARNING, "Program Already Exist...", ButtonType.OK).show();
            return;
        }

        ProgramDTO programDTO = new ProgramDTO(
                txtProgramId.getText(),
                txtProgram.getText(),
                txtDuration.getText(),
                Double.valueOf(txtFee.getText())
        );

        if (programBO.addProgram(programDTO)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Program Added Successfully", ButtonType.OK).show();
            clearFields();
            setProgramID();
            loadProgramsToTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Duplicate Student ID...", ButtonType.OK).show();
        }
    }

    private boolean programAlreadyExist(String programId) {
        return programBO.ifExist(new ProgramDTO(programId));
    }

    public void updateProgramOnAction(ActionEvent actionEvent) {
        if (rowSelected == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row to Update...").show();

        } else {
            ProgramDTO programDTO = new ProgramDTO(
                    txtProgramId.getText(),
                    txtProgram.getText(),
                    txtDuration.getText(),
                    Double.valueOf(txtFee.getText())
            );

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this Program?", yes, no);
            alert.setTitle("Confirmation Alert");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                if (programBO.updateProgram(programDTO)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Program Updated Successfully..", ButtonType.OK).show();

                    clearFields();
                    setProgramID();
                    loadProgramsToTable();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }
            }
        }
    }

    public void deleteProgramOnAction(ActionEvent actionEvent) {

        if (rowSelected == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row to Remove...").show();
        } else {

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Program?", yes, no);
            alert.setTitle("Confirmation Alert");

            Optional<ButtonType> result = alert.showAndWait();

            ProgramDTO programDTO = new ProgramDTO(txtProgramId.getText());

            if (result.orElse(no) == yes) {
                if (programBO.deleteProgram(programDTO)) {

                    tmProgramList.remove(rowSelected);
                    tblProgram.setItems(FXCollections.observableArrayList(tmProgramList));
                    tblProgram.refresh();

                    new Alert(Alert.AlertType.CONFIRMATION, "Program Deleted Successfully", ButtonType.OK).show();
                    clearFields();
                    setProgramID();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again...").show();
                }
            }
        }
    }

    private void clearFields() {
        txtProgramId.clear();
        txtProgram.clear();
        txtDuration.clear();
        txtFee.clear();

        for (TextField field: map.keySet()) {
            field.getParent().setStyle("-fx-border-color: black");
            ((AnchorPane) field.getParent()).getChildren().get(1).setStyle("-fx-text-fill: black");
        }
    }
}
