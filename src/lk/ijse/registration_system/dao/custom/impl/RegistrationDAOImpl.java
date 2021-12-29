package lk.ijse.registration_system.dao.custom.impl;

import lk.ijse.registration_system.dao.custom.RegistrationDAO;
import lk.ijse.registration_system.entity.Registration;
import lk.ijse.registration_system.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RegistrationDAOImpl implements RegistrationDAO {

    @Override
    public boolean add(Registration registration) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(registration);
        transaction.commit();
        session.close();
        return true;
    }
}
