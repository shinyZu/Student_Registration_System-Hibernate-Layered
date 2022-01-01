package lk.ijse.registration_system.dao.custom;

import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.entity.Student;

import java.util.ArrayList;

public interface StudentDAO extends SuperDAO {

    boolean add(Student student);

    boolean update(Student student);

    boolean studentExists(Student student);

    ArrayList<String> getStudentIDs();

    String generateID();

    Student getStudent(String studentId);

    ArrayList<Student> getAll();

    ArrayList<Student> search(String text);
}
