package lk.ijse.registration_system.business;

import lk.ijse.registration_system.business.custom.impl.ProgramBOImpl;
import lk.ijse.registration_system.business.custom.impl.StudentBOImpl;
import lk.ijse.registration_system.business.custom.impl.StudentDetailBOImpl;
import lk.ijse.registration_system.business.custom.impl.VerifyUserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBOFactoryInstance(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT, PROGRAM, STUDENT_DETAIL, VERIFY_USER
    }

    public SuperBO getBO(BOTypes type) {
        switch (type) {
            default:
                return null;
            case STUDENT:
                return new StudentBOImpl();
            case PROGRAM:
                return new ProgramBOImpl();
            case STUDENT_DETAIL:
                return new StudentDetailBOImpl();
            case VERIFY_USER:
                return new VerifyUserBOImpl();
        }
    }
}
