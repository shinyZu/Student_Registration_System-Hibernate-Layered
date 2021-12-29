package lk.ijse.registration_system.business.custom.impl;

import lk.ijse.registration_system.business.custom.StudentBO;
import lk.ijse.registration_system.dao.DAOFactory;
import lk.ijse.registration_system.dao.custom.ProgramDAO;
import lk.ijse.registration_system.dao.custom.QueryDAO;
import lk.ijse.registration_system.dao.custom.RegistrationDAO;
import lk.ijse.registration_system.dao.custom.StudentDAO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.RegistrationDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.entity.Program;
import lk.ijse.registration_system.entity.Registration;
import lk.ijse.registration_system.entity.Student;

import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REGISTRATION);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<String> getAllStudentIds() {
        return studentDAO.getStudentIDs();
    }

    @Override
    public ArrayList<String> getProgramNames() {
        return programDAO.getProgramNames();
    }

    @Override
    public double getProgramFee(String programName) {
        return programDAO.getProgramFee(programName);
    }

    @Override
    public String getProgramID(String programName) {
        return programDAO.getProgramID(programName);
    }

    @Override
    public boolean studentExists(String studentID) {
        return studentDAO.studentExists(new Student(studentID));
    }

    @Override
    public boolean registerStudent(RegistrationDTO dto) {
        System.out.println("RegistrationDTO: "+dto);
        return registrationDAO.add(new Registration(
                dto.getDateOfReg(),
                dto.getUpfrontFee(),
                new Student(dto.getStudentId()),
                new Program(dto.getProgramId())
        ));
    }

    @Override
    public boolean registerStudent(StudentDTO studDTO, RegistrationDTO regDTO) {
        System.out.println("StudentDTO: "+studDTO+", "+"RegistrationDTO: "+regDTO);
        Student student = new Student(
                studDTO.getStudentId(),
                studDTO.getStudentName(),
                studDTO.getAddress(),
                studDTO.getDob(),
                studDTO.getAge(),
                studDTO.getEmail(),
                studDTO.getContactNo()
        );

        Registration registration = new Registration(
                regDTO.getDateOfReg(),
                regDTO.getUpfrontFee(),
                new Student(regDTO.getStudentId()),
                new Program(regDTO.getProgramId())
        );

        if (studentDAO.add(student)) {
            if (registrationDAO.add(registration)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String generateStudentID() {
        return studentDAO.generateID();
    }

    @Override
    public StudentDTO getStudentDetails(String studentId) {
        Student student = studentDAO.getStudent(studentId);
        return new StudentDTO(
                student.getStudentId(),
                student.getStudentName(),
                student.getAddress(),
                student.getDob(),
                student.getAge(),
                student.getEmail(),
                student.getContactNo()
        );
    }

    @Override
    public ArrayList<CustomDTO> getRegisteredPrograms(String studentId) {
        return queryDAO.getRegisteredPrograms(studentId);
    }
}
