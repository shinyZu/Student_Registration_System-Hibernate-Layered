package lk.ijse.registration_system.dao;

import lk.ijse.registration_system.dao.custom.impl.ProgramDAOImpl;
import lk.ijse.registration_system.dao.custom.impl.QueryDAOImpl;
import lk.ijse.registration_system.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.registration_system.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        STUDENT, PROGRAM, REGISTRATION, QUERY
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type) {
            case STUDENT:
                return new StudentDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
