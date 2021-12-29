package lk.ijse.registration_system.business.custom;

import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.RegistrationDTO;
import lk.ijse.registration_system.dto.StudentDTO;

import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    ArrayList<String> getAllStudentIds();

    ArrayList<String> getProgramNames();

    double getProgramFee(String programName);

    String getProgramID(String programName);

    boolean studentExists(String text);

    boolean registerStudent(RegistrationDTO registrationDTO);

    boolean registerStudent(StudentDTO studentDTO, RegistrationDTO registrationDTO);

    String generateStudentID();

    StudentDTO getStudentDetails(String studentId);

    ArrayList<CustomDTO> getRegisteredPrograms(String studentId);
}
