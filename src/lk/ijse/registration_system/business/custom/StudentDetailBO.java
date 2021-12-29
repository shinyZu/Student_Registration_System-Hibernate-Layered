package lk.ijse.registration_system.business.custom;

import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.StudentDTO;

import java.util.ArrayList;

public interface StudentDetailBO extends SuperBO {
    ArrayList<StudentDTO> getAllStudents();

    ArrayList<CustomDTO> getAllRegistrations();
}
