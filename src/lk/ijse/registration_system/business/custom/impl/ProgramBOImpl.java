package lk.ijse.registration_system.business.custom.impl;

import lk.ijse.registration_system.business.custom.ProgramBO;
import lk.ijse.registration_system.dao.DAOFactory;
import lk.ijse.registration_system.dao.custom.ProgramDAO;
import lk.ijse.registration_system.dto.ProgramDTO;
import lk.ijse.registration_system.entity.Program;

import java.util.ArrayList;

public class ProgramBOImpl implements ProgramBO {

    private final ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public boolean addProgram(ProgramDTO dto) {
        System.out.println(dto);
       return programDAO.add(new Program(dto.getProgramId(),dto.getProgramName(),dto.getDuration(),dto.getFee()));
    }

    @Override
    public boolean updateProgram(ProgramDTO dto) {
        return programDAO.update(new Program(dto.getProgramId(),dto.getProgramName(),dto.getDuration(),dto.getFee()));
    }

    @Override
    public boolean deleteProgram(ProgramDTO dto) {
        return programDAO.delete(new Program(dto.getProgramId()));
    }

    @Override
    public String generateProgramID() {
        return programDAO.generateID();
    }

    @Override
    public ArrayList<ProgramDTO> getAllPrograms() {
        ArrayList<ProgramDTO> programList = new ArrayList<>();
        for (Program p : programDAO.getAll()) {
            programList.add(new ProgramDTO(
                    p.getProgramId(),
                    p.getProgramName(),
                    p.getDuration(),
                    p.getFee()
            ));
        }
        return programList;
    }

    @Override
    public boolean ifExist(ProgramDTO programDTO) {
        return programDAO.ifExist(new Program(programDTO.getProgramId()));
    }
}
