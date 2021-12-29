package lk.ijse.registration_system.dao;

import lk.ijse.registration_system.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        STUDENT, PROGRAM, REGISTRATION, QUERY, LOGIN
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type) {
            default:
                return null;
            case STUDENT:
                return new StudentDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
        }
    }
}
