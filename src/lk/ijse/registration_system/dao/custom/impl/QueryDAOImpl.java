package lk.ijse.registration_system.dao.custom.impl;

import lk.ijse.registration_system.dao.custom.QueryDAO;
import lk.ijse.registration_system.dto.CustomDTO;
import lk.ijse.registration_system.entity.Student;
import lk.ijse.registration_system.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getRegisteredPrograms(String studentId) {
        ArrayList<CustomDTO> programList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> resultList = session.createQuery("SELECT s.studentId,p.programName,r.upfrontFee\n" +
                "FROM Student s INNER JOIN Registration r\n" +
                "ON s.studentId = r.student\n" +
                "INNER JOIN Program p\n" +
                "ON r.program = p.programId\n" +
                "WHERE s.studentId=:id").setParameter("id", studentId).list();

        transaction.commit();
        session.close();

        for (Object[] obj : resultList) {
            programList.add(new CustomDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (Double) obj[2]
            ));
        }
        return programList;
    }

    @Override
    public ArrayList<CustomDTO> getRegistrationDetails() {
        ArrayList<CustomDTO> registrationList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        System.out.println();
        List<Object[]> resultList = session.createQuery("SELECT s.studentId, p.programId, p.programName, p.duration, p.fee, r.dateOfReg, r.upfrontFee\n" +
                "FROM Program p INNER JOIN Registration r\n" +
                "ON p.programId = r.program\n"+
                "INNER JOIN Student s\n"+
                "ON r.student = s.studentId").list();

        transaction.commit();
        session.close();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] obj : resultList) {
            registrationList.add(new CustomDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    (Double) obj[4],
                    //formatter.format(obj[5]),
                    (Date) obj[5],
                    (Double) obj[6]
            ));
        }
        System.out.println("registrationList: "+registrationList);
        return registrationList;
    }

    @Override
    public ArrayList<CustomDTO> search(String text) {
        ArrayList<CustomDTO> registrationList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list =session.createQuery("SELECT s.studentId, p.programId, p.programName, p.duration, p.fee, r.dateOfReg, r.upfrontFee \n" +
                "FROM Program p INNER JOIN Registration r\n" +
                "ON p.programId = r.program \n" +
                "INNER JOIN Student s \n" +
                "ON r.student = s.studentId \n" +
                "WHERE p.programName LIKE '%" + text + "%' OR s.studentId LIKE '%" + text + "%'").list();
        transaction.commit();
        session.close();

        for (Object[] obj : list) {
            registrationList.add(new CustomDTO(
                    (String) obj[0],
                    (String) obj[1],
                    (String) obj[2],
                    (String) obj[3],
                    (Double) obj[4],
                    //formatter.format(obj[5]),
                    (Date) obj[5],
                    (Double) obj[6]
            ));
        }
        return registrationList;
    }
}
