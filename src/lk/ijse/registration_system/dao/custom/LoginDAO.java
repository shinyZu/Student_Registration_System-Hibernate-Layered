package lk.ijse.registration_system.dao.custom;

import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.entity.Login;

public interface LoginDAO  extends SuperDAO {
    boolean isExist(Login login);

    boolean add(Login login);

    boolean isPasswordAvailable(Login login);
}
