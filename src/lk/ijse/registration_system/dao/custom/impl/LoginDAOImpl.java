package lk.ijse.registration_system.dao.custom.impl;

import lk.ijse.registration_system.dao.custom.LoginDAO;
import lk.ijse.registration_system.entity.Login;
import lk.ijse.registration_system.entity.Program;
import lk.ijse.registration_system.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginDAOImpl implements LoginDAO {

    @Override
    public boolean isExist(Login login) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Login result = (Login) session.createQuery("FROM Login l WHERE l.userName = :userName AND l.password = :password")
                .setParameter("userName", login.getUserName())
                .setParameter("password", login.getPassword())
                .uniqueResult();
        transaction.commit();
        session.close();
        System.out.println("login: "+result);
        return result != null;
    }

    @Override
    public boolean add(Login login) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(login);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean isPasswordAvailable(Login login) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String password = (String) session.createQuery("SELECT l.password FROM Login l WHERE l.password = :password").setParameter("password", login.getPassword()).uniqueResult();
        transaction.commit();
        session.close();
        System.out.println("password: "+password);
        return password==null;
    }
}
