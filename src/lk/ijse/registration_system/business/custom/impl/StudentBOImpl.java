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

    // if the Student is already registered
    @Override
    public boolean registerStudent(RegistrationDTO dto) {
       // System.out.println("RegistrationDTO: "+dto);
        return registrationDAO.add(new Registration(
                dto.getDateOfReg(),
                dto.getUpfrontFee(),
                new Student(dto.getStudentId()),
                new Program(dto.getProgramId())
        ));
    }

    // if the Student is a new student
    @Override
    public boolean registerStudent(StudentDTO studDTO, RegistrationDTO regDTO) {
        //System.out.println("StudentDTO: "+studDTO+", "+"RegistrationDTO: "+regDTO);
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
            /*if (registrationDAO.add(student)){*/
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
        System.out.println("student: "+student);

        /**If in Lazy fetching mode in Student entity
         *
         * it will execute the query to fetch only the student details(only parent entity data) NOT registration details(no join query is executed)
         * hence will need to execute a separate query to fetch registration details
         * will fetch only parent entity data
         * */

        if (student != null) {
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
        return null;

        /** If Eager fetching is enabled in Student entity
         *
         * it will execute only one query to fetch all student details including the registration details(will execute a join query which will fetch all data)
         * no need to explicitly execute a query to get registration details
         * will fetch both parent entity and child entity data in one single query
         * */
       /* if (student != null) {
            return new StudentDTO(
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getAddress(),
                    student.getDob(),
                    student.getAge(),
                    student.getEmail(),
                    student.getContactNo(),
                    student.getRegDetails()
            );
        }
        return null;*/
    }

    @Override
    public ArrayList<CustomDTO> getRegisteredPrograms(String studentId) {
        return queryDAO.getRegisteredPrograms(studentId);
    }

    @Override
    public boolean updateStudent(StudentDTO studDTO) {
        return studentDAO.update(new Student(
                studDTO.getStudentId(),
                studDTO.getStudentName(),
                studDTO.getAddress(),
                studDTO.getDob(),
                studDTO.getAge(),
                studDTO.getEmail(),
                studDTO.getContactNo()
        ));
    }
}
