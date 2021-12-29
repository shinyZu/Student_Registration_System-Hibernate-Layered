package lk.ijse.registration_system.business.custom.impl;

import lk.ijse.registration_system.business.custom.StudentDetailBO;
import lk.ijse.registration_system.dao.DAOFactory;
import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.dao.custom.QueryDAO;
import lk.ijse.registration_system.dao.custom.StudentDAO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.dto.StudentDTO;
import lk.ijse.registration_system.entity.Student;

import java.util.ArrayList;

public class StudentDetailBOImpl implements StudentDetailBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<StudentDTO> getAllStudents() {
        ArrayList<StudentDTO> studentList = new ArrayList<>();

        for (Student s : studentDAO.getAll() ) {
            studentList.add(new StudentDTO(
                s.getStudentId(),
                s.getStudentName(),
                s.getAddress(),
                s.getDob(),
                s.getAge(),
                s.getEmail(),
                s.getContactNo()
            ));
        }
        return studentList;
    }

    @Override
    public ArrayList<CustomDTO> getAllRegistrations() {
       return queryDAO.getRegistrationDetails();
    }

}
