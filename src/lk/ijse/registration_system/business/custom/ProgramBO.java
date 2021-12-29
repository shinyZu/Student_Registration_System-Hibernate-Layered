package lk.ijse.registration_system.business.custom;

import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.dto.ProgramDTO;

import java.util.ArrayList;

public interface ProgramBO extends SuperBO {

    boolean addProgram(ProgramDTO programDTO);

    boolean updateProgram(ProgramDTO programDTO);

    boolean deleteProgram(ProgramDTO programDTO);

    String generateProgramID();

    ArrayList<ProgramDTO> getAllPrograms();

    boolean ifExist(ProgramDTO programDTO);
}
