package lk.ijse.registration_system.dao.custom.impl;

import lk.ijse.registration_system.dao.custom.StudentDAO;
import lk.ijse.registration_system.entity.Student;
import lk.ijse.registration_system.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean add(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean studentExists(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student stud = session.get(Student.class, student.getStudentId());
        transaction.commit();
        session.close();
        System.out.println("stud: "+stud);
        System.out.println(stud!=null);
        return stud!=null;
    }

    @Override
    public ArrayList<String> getStudentIDs() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> list = session.createQuery("SELECT s.studentId FROM Student s").list();
        transaction.commit();
        session.close();
        return (ArrayList<String>) list;
    }

    @Override
    public String generateID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery query = session.createSQLQuery("SELECT s.studentId FROM Student s ORDER BY s.studentId DESC LIMIT 1");
        String studentID = (String) query.uniqueResult();
        transaction.commit();
        session.close();

        if (studentID != null) {
            int tempId = Integer.parseInt(studentID.split("S")[1]);
            System.out.println("tempId: "+tempId);
            tempId = tempId + 1;

            if (tempId <= 9) {
                return "S00" + tempId;
            } else if (tempId <= 99) {
                return "S0" + tempId;
            } else {
                return "S" + tempId;
            }
        } else {
            return "S001";
        }
    }

    @Override
    public Student getStudent(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, studentId);
        transaction.commit();
        session.close();
        return student;
    }

    @Override
    public ArrayList<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("before getAll");
        ArrayList<Student> studentList = (ArrayList<Student>) session.createQuery("FROM Student ").list();
        System.out.println("after getAll");
        transaction.commit();
        session.close();
        System.out.println("studentList in getAll: "+studentList);
//        System.out.println("index8: "+studentList.get(8).getRegDetails());
        return studentList;
    }

    @Override
    public ArrayList<Student> search(String text) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Student> studentList = (ArrayList<Student>) session.createQuery("FROM Student s WHERE s.studentName LIKE '%" + text + "%' OR s.address LIKE '%" + text + "%'").list();
        transaction.commit();
        session.close();
        return studentList;
    }


}
