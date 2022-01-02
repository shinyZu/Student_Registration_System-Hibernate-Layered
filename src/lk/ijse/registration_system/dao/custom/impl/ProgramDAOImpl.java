package lk.ijse.registration_system.dao.custom.impl;

import lk.ijse.registration_system.dao.custom.ProgramDAO;
import lk.ijse.registration_system.entity.Program;
import lk.ijse.registration_system.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {

    @Override
    public boolean add(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(program);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(program);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Program.class,program.getProgramId()));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery query = session.createSQLQuery("SELECT p.programId FROM Program p ORDER BY p.programId DESC LIMIT 1");
        String programID = (String) query.uniqueResult();
        transaction.commit();
        session.close();

        if (programID != null) {
            int tempId = Integer.parseInt(programID.split("T")[1]);
            //System.out.println("tempId: "+tempId);
            tempId = tempId + 1;

            if (tempId <= 9) {
                return "CT000" + tempId;
            } else if (tempId <= 99) {
                return "CT00" + tempId;
            } else {
                return "CT0" + tempId;
            }
        } else {
            return "CT0001";
        }
    }

    @Override
    public ArrayList<Program> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Program> programList = (ArrayList<Program>) session.createQuery("FROM Program").list();
        transaction.commit();
        session.close();
        return programList;
    }

    @Override
    public ArrayList<String> getProgramNames() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> list = session.createQuery("SELECT p.programName FROM Program p").list();
        transaction.commit();
        session.close();
        return (ArrayList<String>) list;
    }

    @Override
    public double getProgramFee(String programName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        double fee = (double) session.createQuery("SELECT p.fee FROM Program p WHERE p.programName = :programName")
                .setParameter("programName",programName).uniqueResult();
        transaction.commit();
        session.close();
        return fee;
    }

    @Override
    public String getProgramID(String programName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String id = (String) session.createQuery("SELECT p.programId FROM Program p WHERE p.programName = :programName")
                .setParameter("programName",programName).uniqueResult();
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public boolean ifExist(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        //String result = (String) session.createQuery("SELECT p.programId FROM Program p WHERE p.programId = :programId").setParameter("programId", program).uniqueResult();
        Program result = session.get(Program.class, program.getProgramId());
        transaction.commit();
        session.close();
        //System.out.println("program: "+result);
        return result != null;
    }
}
