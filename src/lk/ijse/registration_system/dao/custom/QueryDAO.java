package lk.ijse.registration_system.dao.custom;

import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.dto.CustomDTO;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<CustomDTO> getRegisteredPrograms(String studentId);

    ArrayList<CustomDTO> getRegistrationDetails();
}
